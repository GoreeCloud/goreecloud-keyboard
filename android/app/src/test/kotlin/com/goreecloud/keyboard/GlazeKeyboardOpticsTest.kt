package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlazeKeyboardOpticsTest {
    @Test
    fun ordinaryKeyboardOpticsRemainNeutralAndSemanticFirst() {
        val state = GlazeKeyboardOptics.resolve()

        assertEquals(GlazeKeyboardOptics.State.Mode.NEUTRAL_OPTICAL, state.mode)
        assertEquals(1f, state.semanticProtection)
        assertEquals(0f, state.environmentalColorMemoryInfluence)
        assertFalse(state.decorativeTintAllowed)
    }

    @Test
    fun increasedContrastRaisesFrostWithoutEnablingDecoration() {
        val normal = GlazeKeyboardOptics.resolve()
        val contrast = GlazeKeyboardOptics.resolve(
            GlazeKeyboardOptics.Accessibility(increasedContrast = true)
        )

        assertTrue(contrast.frostStrength > normal.frostStrength)
        assertEquals(0f, contrast.environmentalColorMemoryInfluence)
        assertFalse(contrast.decorativeTintAllowed)
    }

    @Test
    fun reducedTransparencyFailsClosedToSolidAccessible() {
        val state = GlazeKeyboardOptics.resolve(
            GlazeKeyboardOptics.Accessibility(reducedTransparency = true)
        )

        assertEquals(GlazeKeyboardOptics.State.Mode.SOLID_ACCESSIBLE, state.mode)
        assertEquals(0f, state.blurScale)
        assertEquals(1f, state.semanticProtection)
        assertFalse(state.decorativeTintAllowed)
    }

    @Test
    fun forcedColorsFailsClosedToSolidAccessible() {
        val state = GlazeKeyboardOptics.resolve(
            GlazeKeyboardOptics.Accessibility(forcedColors = true)
        )

        assertEquals(GlazeKeyboardOptics.State.Mode.SOLID_ACCESSIBLE, state.mode)
        assertEquals(0f, state.blurScale)
        assertFalse(state.decorativeTintAllowed)
    }

    @Test
    fun sensitiveInputSourcesCanNeverDriveOptics() {
        assertFalse(GlazeKeyboardOptics.EditorContentMayDriveOptics)
        assertFalse(GlazeKeyboardOptics.ClipboardMayDriveOptics)
        assertFalse(GlazeKeyboardOptics.SuggestionContentMayDriveOptics)
        assertFalse(GlazeKeyboardOptics.AppIdentityMayDriveOptics)
        assertFalse(GlazeKeyboardOptics.RemoteContextAllowed)
        assertFalse(GlazeKeyboardOptics.TelemetryRequired)
        assertEquals(0f, GlazeKeyboardOptics.MaxEnvironmentalColorMemoryInfluence)
    }
}
