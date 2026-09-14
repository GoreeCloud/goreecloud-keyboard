package com.goreecloud.keyboard

/**
 * Keyboard-specific GLAZE UI V1.4 Optical Intelligence resolver.
 *
 * This resolver is intentionally stricter than the generic Glaze optical engine:
 * editor text, composing text, suggestions, clipboard state, application identity,
 * surrounding text, emoji history, and key history are never accepted as context.
 * Keyboard uses only non-content local presentation/accessibility state.
 */
internal object GlazeKeyboardOptics {
    const val TargetVersion = "1.4.0"
    const val StableSourceRevision = "84cb3db4884042f0fa25ed6d475a127fb110f596"
    const val MaxEnvironmentalColorMemoryInfluence = 0f
    const val EditorContentMayDriveOptics = false
    const val ClipboardMayDriveOptics = false
    const val SuggestionContentMayDriveOptics = false
    const val AppIdentityMayDriveOptics = false
    const val RemoteContextAllowed = false
    const val TelemetryRequired = false

    data class Accessibility(
        val reducedTransparency: Boolean = false,
        val increasedContrast: Boolean = false,
        val forcedColors: Boolean = false,
    )

    data class State(
        val mode: Mode,
        val frostStrength: Float,
        val blurScale: Float,
        val semanticProtection: Float,
        val decorativeTintAllowed: Boolean,
        val environmentalColorMemoryInfluence: Float,
    ) {
        enum class Mode { NEUTRAL_OPTICAL, SOLID_ACCESSIBLE }
    }

    /**
     * Keyboard keeps semantic importance at the maximum because key labels,
     * suggestions, and editor actions must remain immediately legible.
     */
    fun resolve(accessibility: Accessibility = Accessibility()): State {
        if (accessibility.forcedColors || accessibility.reducedTransparency) {
            return State(
                mode = State.Mode.SOLID_ACCESSIBLE,
                frostStrength = 1f,
                blurScale = 0f,
                semanticProtection = 1f,
                decorativeTintAllowed = false,
                environmentalColorMemoryInfluence = 0f,
            )
        }

        val semanticProtection = 1f
        val blurScale = 0.58f
        val frostStrength = if (accessibility.increasedContrast) 0.76f else 0.66f

        return State(
            mode = State.Mode.NEUTRAL_OPTICAL,
            frostStrength = frostStrength,
            blurScale = blurScale,
            semanticProtection = semanticProtection,
            decorativeTintAllowed = false,
            environmentalColorMemoryInfluence = 0f,
        )
    }
}
