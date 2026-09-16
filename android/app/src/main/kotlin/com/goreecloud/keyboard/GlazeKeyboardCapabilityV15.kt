package com.goreecloud.keyboard

/**
 * Keyboard-specific native projection of the bounded GLAZE UI V1.5.0 Stable
 * context/capability presentation contract.
 *
 * This object accepts only explicit capability state from the authority that owns
 * it. Typed text, composing text, surrounding text, suggestions, clipboard state,
 * application identity, emoji history, and key history are never capability or
 * context inputs. Glaze cannot grant permission, infer authorization, select a
 * provider winner, navigate, or execute an action.
 */
internal object GlazeKeyboardCapabilityV15 {
    const val TargetVersion = "1.5.0"
    const val StableSourceRevision = "b7fa8164bfdeaa1dc0acb21b770e7601120da04e"
    const val ReviewedImplementationAnchor = "ee1032a0822ab8e103f8afe48e5c1859fde65cc9"
    const val OpticalBaselineVersion = "1.4.1"
    const val OpticalBaselineRevision = "4fab9da0fad2e5c974e0e66ec88632c61745751c"
    const val RollbackVersion = "1.4.1"

    const val TypedContentMayDriveCapabilityPresentation = false
    const val ComposingContentMayDriveCapabilityPresentation = false
    const val SurroundingTextMayDriveCapabilityPresentation = false
    const val SuggestionContentMayDriveCapabilityPresentation = false
    const val ClipboardMayDriveCapabilityPresentation = false
    const val AppIdentityMayDriveCapabilityPresentation = false
    const val RemoteContextAllowed = false
    const val TelemetryRequired = false

    enum class CapabilityState {
        AVAILABLE,
        TEMPORARILY_UNAVAILABLE,
        RESTRICTED,
        UNKNOWN,
        CONFLICT,
    }

    data class Capability(
        val id: String,
        val state: CapabilityState,
        val authorityDomain: String,
    )

    data class Action(
        val id: String,
        val requiredCapabilityIds: Set<String>,
        val consequential: Boolean = false,
    )

    data class Presentation(
        val actionId: String,
        val enabled: Boolean,
        val state: CapabilityState,
        val reasonCodes: Set<String>,
        val automaticExecutionAllowed: Boolean = false,
        val authorizationInferred: Boolean = false,
        val providerPrecedenceInferred: Boolean = false,
    )

    fun resolve(
        action: Action,
        capabilities: Collection<Capability>,
    ): Presentation {
        if (action.requiredCapabilityIds.isEmpty()) {
            return Presentation(
                actionId = action.id,
                enabled = true,
                state = CapabilityState.AVAILABLE,
                reasonCodes = emptySet(),
            )
        }

        val byId = capabilities.groupBy { it.id }
        val reasons = linkedSetOf<String>()
        var resolved = CapabilityState.AVAILABLE

        for (capabilityId in action.requiredCapabilityIds.sorted()) {
            val records = byId[capabilityId].orEmpty()
            val state = when {
                records.isEmpty() -> CapabilityState.UNKNOWN
                records.size > 1 -> CapabilityState.CONFLICT
                else -> records.single().state
            }
            resolved = strongest(resolved, state)
            when (state) {
                CapabilityState.AVAILABLE -> Unit
                CapabilityState.TEMPORARILY_UNAVAILABLE -> reasons += "temporarily-unavailable:$capabilityId"
                CapabilityState.RESTRICTED -> reasons += "restricted-by-authority:$capabilityId"
                CapabilityState.UNKNOWN -> reasons += "capability-unknown:$capabilityId"
                CapabilityState.CONFLICT -> reasons += "capability-conflict:$capabilityId"
            }
        }

        return Presentation(
            actionId = action.id,
            enabled = resolved == CapabilityState.AVAILABLE,
            state = resolved,
            reasonCodes = reasons,
        )
    }

    private fun strongest(current: CapabilityState, candidate: CapabilityState): CapabilityState {
        val order = mapOf(
            CapabilityState.AVAILABLE to 0,
            CapabilityState.TEMPORARILY_UNAVAILABLE to 1,
            CapabilityState.RESTRICTED to 2,
            CapabilityState.UNKNOWN to 3,
            CapabilityState.CONFLICT to 4,
        )
        return if (order.getValue(candidate) > order.getValue(current)) candidate else current
    }
}
