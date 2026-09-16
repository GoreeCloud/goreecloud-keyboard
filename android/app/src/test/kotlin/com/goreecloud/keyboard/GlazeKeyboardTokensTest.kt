package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentOpticalMappingPinsExactGlazeUiV141StableAuthority() {
        assertEquals("1.4.1", GlazeKeyboardTokens.TargetVersion)
        assertEquals(
            "4fab9da0fad2e5c974e0e66ec88632c61745751c",
            GlazeKeyboardTokens.SourceRevision
        )
        assertEquals(GlazeKeyboardOptics.TargetVersion, GlazeKeyboardTokens.TargetVersion)
        assertEquals(GlazeKeyboardOptics.StableSourceRevision, GlazeKeyboardTokens.SourceRevision)
        assertEquals("1.5.0", GlazeKeyboardCapabilityV15.TargetVersion)
        assertEquals("1.4.1", GlazeKeyboardCapabilityV15.OpticalBaselineVersion)
        assertEquals(
            GlazeKeyboardTokens.SourceRevision,
            GlazeKeyboardCapabilityV15.OpticalBaselineRevision,
        )
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
    fun opticalGeometryRemainsSeparateFromStructuralRadiusAndTargets() {
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
    fun inheritedInteractionStateCalibrationRemainsExplicit() {
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
    fun deepDarkUsesExplicitNeutralMaterialValues() {
        val deepDark = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DEEP_DARK)
        assertEquals(0xFF05070A.toInt(), deepDark.canvasArgb)
        assertEquals(0xB80E0E10.toInt(), deepDark.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), deepDark.onSurfaceArgb)
        assertEquals(0xFFABB4C2.toInt(), deepDark.onSurfaceMutedArgb)
        assertEquals(0x17FFFFFF, deepDark.lineArgb)
    }

    @Test
    fun inheritedAtmosphereCannotTintSubstrateOrEnableSensitiveObservation() {
        assertEquals(0f, GlazeKeyboardAtmosphere.DefaultMaterialTintContribution)
        assertFalse(GlazeKeyboardAtmosphere.TealAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.GreenAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AquaAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AmberAsBaseMaterialAllowed)
        assertFalse(GlazeKeyboardAtmosphere.BrandColorMayDefineSubstrate)
        assertFalse(GlazeKeyboardAtmosphere.SemanticColorMayDefineSubstrate)
        assertFalse(GlazeKeyboardAtmosphere.EnvironmentalAuraOptional)
        assertFalse(GlazeKeyboardAtmosphere.EnvironmentalAuraMayPassThroughBackdrop)
        assertFalse(GlazeKeyboardAtmosphere.EnvironmentalColorMemoryEnabled)
        assertEquals(0f, GlazeKeyboardAtmosphere.EnvironmentalColorMemoryMaxInfluence)
        assertFalse(GlazeKeyboardAtmosphere.EditorContentSamplingAllowed)
        assertFalse(GlazeKeyboardAtmosphere.SuggestionContentSamplingAllowed)
        assertFalse(GlazeKeyboardAtmosphere.ClipboardSamplingAllowed)
        assertFalse(GlazeKeyboardAtmosphere.ApplicationIdentitySamplingAllowed)
        assertFalse(GlazeKeyboardAtmosphere.RemoteColorDerivationAllowed)
        assertFalse(GlazeKeyboardAtmosphere.PersistentSampleHistoryAllowed)
        assertFalse(GlazeKeyboardAtmosphere.SemanticInferenceAllowed)
        assertFalse(GlazeKeyboardAtmosphere.AnimatedAtmosphereEnabled)
    }
}
