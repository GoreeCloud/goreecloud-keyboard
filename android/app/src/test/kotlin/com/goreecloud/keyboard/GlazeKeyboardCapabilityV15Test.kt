package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlazeKeyboardCapabilityV15Test {
    private fun capability(
        id: String,
        state: GlazeKeyboardCapabilityV15.CapabilityState,
        authority: String = "keyboard",
    ) = GlazeKeyboardCapabilityV15.Capability(id, state, authority)

    @Test
    fun exactStableAuthorityAndInheritedOpticalBaselineArePinned() {
        assertEquals("1.5.0", GlazeKeyboardCapabilityV15.TargetVersion)
        assertEquals(
            "b7fa8164bfdeaa1dc0acb21b770e7601120da04e",
            GlazeKeyboardCapabilityV15.StableSourceRevision,
        )
        assertEquals(
            "ee1032a0822ab8e103f8afe48e5c1859fde65cc9",
            GlazeKeyboardCapabilityV15.ReviewedImplementationAnchor,
        )
        assertEquals("1.4.1", GlazeKeyboardCapabilityV15.OpticalBaselineVersion)
        assertEquals("1.4.1", GlazeKeyboardCapabilityV15.RollbackVersion)
    }

    @Test
    fun sensitiveImeStateCannotDriveCapabilityPresentation() {
        assertFalse(GlazeKeyboardCapabilityV15.TypedContentMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.ComposingContentMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.SurroundingTextMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.SuggestionContentMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.ClipboardMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.AppIdentityMayDriveCapabilityPresentation)
        assertFalse(GlazeKeyboardCapabilityV15.RemoteContextAllowed)
        assertFalse(GlazeKeyboardCapabilityV15.TelemetryRequired)
    }

    @Test
    fun availableCapabilityDoesNotAuthorizeAutomaticExecution() {
        val result = GlazeKeyboardCapabilityV15.resolve(
            GlazeKeyboardCapabilityV15.Action(
                id = "commit-suggestion",
                requiredCapabilityIds = setOf("keyboard.suggestion.commit"),
                consequential = true,
            ),
            listOf(
                capability(
                    "keyboard.suggestion.commit",
                    GlazeKeyboardCapabilityV15.CapabilityState.AVAILABLE,
                ),
            ),
        )

        assertTrue(result.enabled)
        assertFalse(result.automaticExecutionAllowed)
        assertFalse(result.authorizationInferred)
        assertFalse(result.providerPrecedenceInferred)
    }

    @Test
    fun missingCapabilityFailsClosed() {
        val result = GlazeKeyboardCapabilityV15.resolve(
            GlazeKeyboardCapabilityV15.Action(
                id = "sync-preferences",
                requiredCapabilityIds = setOf("sync.keyboard.preferences"),
            ),
            emptyList(),
        )

        assertFalse(result.enabled)
        assertEquals(GlazeKeyboardCapabilityV15.CapabilityState.UNKNOWN, result.state)
        assertTrue(result.reasonCodes.contains("capability-unknown:sync.keyboard.preferences"))
    }

    @Test
    fun duplicateCapabilityOwnershipFailsClosedWithoutInventedPrecedence() {
        val result = GlazeKeyboardCapabilityV15.resolve(
            GlazeKeyboardCapabilityV15.Action(
                id = "remote-assistance",
                requiredCapabilityIds = setOf("keyboard.remote-assistance"),
            ),
            listOf(
                capability(
                    "keyboard.remote-assistance",
                    GlazeKeyboardCapabilityV15.CapabilityState.AVAILABLE,
                    "provider-a",
                ),
                capability(
                    "keyboard.remote-assistance",
                    GlazeKeyboardCapabilityV15.CapabilityState.AVAILABLE,
                    "provider-b",
                ),
            ),
        )

        assertFalse(result.enabled)
        assertEquals(GlazeKeyboardCapabilityV15.CapabilityState.CONFLICT, result.state)
        assertTrue(result.reasonCodes.contains("capability-conflict:keyboard.remote-assistance"))
        assertFalse(result.providerPrecedenceInferred)
    }

    @Test
    fun privacyRestrictedCapabilityRemainsDisabled() {
        val result = GlazeKeyboardCapabilityV15.resolve(
            GlazeKeyboardCapabilityV15.Action(
                id = "cloud-feature",
                requiredCapabilityIds = setOf("authorization.keyboard.remote-processing"),
            ),
            listOf(
                capability(
                    "authorization.keyboard.remote-processing",
                    GlazeKeyboardCapabilityV15.CapabilityState.RESTRICTED,
                    "privacy-shield",
                ),
            ),
        )

        assertFalse(result.enabled)
        assertEquals(GlazeKeyboardCapabilityV15.CapabilityState.RESTRICTED, result.state)
        assertTrue(
            result.reasonCodes.contains(
                "restricted-by-authority:authorization.keyboard.remote-processing"
            )
        )
        assertFalse(result.authorizationInferred)
    }
}
