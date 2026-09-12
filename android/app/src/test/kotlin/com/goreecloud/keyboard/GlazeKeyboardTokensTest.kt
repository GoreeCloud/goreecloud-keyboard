package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentMappingPinsExactGlazeUiV13StableAuthority() {
        assertEquals("1.3.0", GlazeKeyboardTokens.TargetVersion)
        assertEquals("Adaptive Resonance", GlazeKeyboardTokens.ReleaseTheme)
        assertEquals(
            "fc7cc91d2eace8da2371371c2855c24cbcb326a1",
            GlazeKeyboardTokens.SourceRevision
        )
        assertEquals(
            "tokens/glaze-v1.2-optical-foundation.candidate.json",
            GlazeKeyboardTokens.OpticalContract
        )
        assertEquals(
            "contracts/v1.3/adaptive-resonance.plan.json",
            GlazeKeyboardTokens.AdaptiveContract
        )
        assertEquals("css/glaze-v1.3.0.css", GlazeKeyboardTokens.StableWebEntrypoint)
        assertEquals("js/glaze-v1.3.0.mjs", GlazeKeyboardTokens.StableRuntimeEntrypoint)
        assertEquals("1.2.0", GlazeKeyboardTokens.RollbackBaselineVersion)
    }

    @Test
    fun inheritedGeometryAndInteractionFloorsRemainStable() {
        assertEquals(4f, GlazeKeyboardTokens.Space1Dp)
        assertEquals(8f, GlazeKeyboardTokens.Space2Dp)
        assertEquals(12f, GlazeKeyboardTokens.RadiusMediumDp)
        assertEquals(48f, GlazeKeyboardTokens.GeneralInteractionFloorDp)
        assertEquals(56f, GlazeKeyboardTokens.TouchAssistanceInteractionFloorDp)
        assertEquals(
            GlazeKeyboardTokens.GeneralInteractionFloorDp,
            GlazeKeyboardTokens.SuggestionStripHeightDp
        )
    }

    @Test
    fun inheritedOpticalGeometryRemainsSeparateFromStructuralRadiusAndTargets() {
        assertEquals(8f, GlazeKeyboardTokens.OpticalMicroDp)
        assertEquals(16f, GlazeKeyboardTokens.OpticalControlDp)
        assertEquals(24f, GlazeKeyboardTokens.OpticalContainerDp)
        assertEquals(32f, GlazeKeyboardTokens.OpticalHeroDp)
        assertEquals(999f, GlazeKeyboardTokens.OpticalCapsuleDp)
        assertEquals(12f, GlazeKeyboardTokens.RadiusMediumDp)
        assertEquals(48f, GlazeKeyboardTokens.GeneralInteractionFloorDp)
    }

    @Test
    fun touchAssistanceRaisesInteractionFloorWithoutChangingNormalGeometry() {
        assertEquals(48f, GlazeKeyboardTokens.interactionFloorDp(touchAssistance = false))
        assertEquals(56f, GlazeKeyboardTokens.interactionFloorDp(touchAssistance = true))
    }

    @Test
    fun inheritedInteractionStateCalibrationIsExplicit() {
        assertEquals(0.095f, GlazeKeyboardTokens.PressedOverlayOpacity)
        assertEquals(0.12f, GlazeKeyboardTokens.SelectedOverlayOpacity)
        assertEquals(3f, GlazeKeyboardTokens.FocusWidthDp)
        assertEquals(4f, GlazeKeyboardTokens.IncreasedContrastFocusWidthDp)
        assertEquals(
            0x18151A23,
            GlazeKeyboardTokens.stateOverlayArgb(
                GlazeKeyboardTokens.Appearance.LIGHT,
                GlazeKeyboardTokens.PressedOverlayOpacity,
            )
        )
    }

    @Test
    fun lightAndDarkUseInheritedNeutralFrostedMaterial() {
        val light = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT)
        assertEquals(0xFFF5F7FA.toInt(), light.canvasArgb)
        assertEquals(0x94FFFFFF.toInt(), light.surfaceArgb)
        assertEquals(0xFF151A23.toInt(), light.onSurfaceArgb)
        assertEquals(0xFF5D6675.toInt(), light.onSurfaceMutedArgb)
        assertEquals(0x1A505050, light.lineArgb)

        val dark = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DARK)
        assertEquals(0xFF0B0D11.toInt(), dark.canvasArgb)
        assertEquals(0x9E19191B.toInt(), dark.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), dark.onSurfaceArgb)
        assertEquals(0xFFB0B7C3.toInt(), dark.onSurfaceMutedArgb)
        assertEquals(0x1AFFFFFF, dark.lineArgb)
        assertNotEquals(light, dark)
    }

    @Test
    fun deepDarkUsesExplicitInheritedNeutralMaterialValues() {
        val deepDark = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DEEP_DARK)
        assertEquals(0xFF05070A.toInt(), deepDark.canvasArgb)
        assertEquals(0xB80E0E10.toInt(), deepDark.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), deepDark.onSurfaceArgb)
        assertEquals(0xFFABB4C2.toInt(), deepDark.onSurfaceMutedArgb)
        assertEquals(0x17FFFFFF, deepDark.lineArgb)
    }

    @Test
    fun v13AdaptiveExpressionCannotTintSubstrateOrEnableEditorObservation() {
        assertEquals(0f, GlazeKeyboardAtmosphere.DefaultMaterialTintContribution)
        assertFalse(GlazeKeyboardAtmosphere.TealAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.GreenAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AquaAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AmberAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.BrandColorMayDefineSubstrate)
        assertFalse(GlazeKeyboardAtmosphere.SemanticColorMayDefineSubstrate)
        assertFalse(GlazeKeyboardAtmosphere.EnvironmentalColorMemoryEnabled)
        assertFalse(GlazeKeyboardAtmosphere.RemoteColorDerivationAllowed)
        assertFalse(GlazeKeyboardAtmosphere.PersistentSampleHistoryAllowed)
        assertFalse(GlazeKeyboardAtmosphere.SemanticInferenceAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AnimatedAtmosphereEnabled)
    }
}
