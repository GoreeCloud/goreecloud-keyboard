package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentMappingPinsExactGlazeUiV11Authority() {
        assertEquals("1.1.0", GlazeKeyboardTokens.TargetVersion)
        assertEquals(
            "15cc76d2bcd4065552dc31c77145b63f34d9e7b2",
            GlazeKeyboardTokens.SourceRevision
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
    fun v11OpticalGeometryIsSeparateFromStructuralRadiusAndTargets() {
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
    fun lightAndDarkPreserveInheritedV1StructuralMapping() {
        val light = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT)
        assertEquals(0xFFF5F7FA.toInt(), light.canvasArgb)
        assertEquals(0xC7FFFFFF.toInt(), light.surfaceArgb)
        assertEquals(0xFF151A23.toInt(), light.onSurfaceArgb)
        assertEquals(0xFF5D6675.toInt(), light.onSurfaceMutedArgb)
        assertEquals(0x24192332, light.lineArgb)

        val dark = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DARK)
        assertEquals(0xFF0B0D11.toInt(), dark.canvasArgb)
        assertEquals(0xC2181D26.toInt(), dark.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), dark.onSurfaceArgb)
        assertEquals(0xFFB0B7C3.toInt(), dark.onSurfaceMutedArgb)
        assertEquals(0x24E1E8F4, dark.lineArgb)
        assertNotEquals(light, dark)
    }

    @Test
    fun deepDarkUsesExplicitV11StructuralAppearanceValues() {
        val deepDark = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DEEP_DARK)
        assertEquals(0xFF05070A.toInt(), deepDark.canvasArgb)
        assertEquals(0xCC12161D.toInt(), deepDark.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), deepDark.onSurfaceArgb)
        assertEquals(0xFFABB4C2.toInt(), deepDark.onSurfaceMutedArgb)
        assertEquals(0x26E1E8F4, deepDark.lineArgb)
    }

    @Test
    fun atmosphereContractCannotEnableSamplingPersistenceOrSemanticInference() {
        assertEquals(0xFF0F6B6F.toInt(), GlazeKeyboardAtmosphere.DeepTealArgb)
        assertEquals(0xFFD9A35F.toInt(), GlazeKeyboardAtmosphere.SoftAmberArgb)
        assertFalse(GlazeKeyboardAtmosphere.EnvironmentalColorMemoryEnabled)
        assertFalse(GlazeKeyboardAtmosphere.RemoteColorDerivationAllowed)
        assertFalse(GlazeKeyboardAtmosphere.PersistentSampleHistoryAllowed)
        assertFalse(GlazeKeyboardAtmosphere.SemanticInferenceAllowed)
    }
}
