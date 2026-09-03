package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentMappingPinsExactGlazeUiV1Authority() {
        assertEquals("1.0.0", GlazeKeyboardTokens.TargetVersion)
        assertEquals(
            "70909bbdccad378fb7281ae1842e2f5beed64c38",
            GlazeKeyboardTokens.SourceRevision
        )
    }

    @Test
    fun currentMappedGeometryUsesGlazeUiV1Values() {
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
    fun touchAssistanceRaisesInteractionFloorWithoutChangingNormalGeometry() {
        assertEquals(48f, GlazeKeyboardTokens.interactionFloorDp(touchAssistance = false))
        assertEquals(56f, GlazeKeyboardTokens.interactionFloorDp(touchAssistance = true))
    }

    @Test
    fun currentLightFoundationUsesCanonicalGlazeUiV1Tokens() {
        val palette = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT)
        assertEquals(0xFFF5F7FA.toInt(), palette.canvasArgb)
        assertEquals(0xC7FFFFFF.toInt(), palette.surfaceArgb)
        assertEquals(0xFF151A23.toInt(), palette.onSurfaceArgb)
        assertEquals(0xFF5D6675.toInt(), palette.onSurfaceMutedArgb)
        assertEquals(0x24192332, palette.lineArgb)
    }

    @Test
    fun currentDarkFoundationUsesCanonicalGlazeUiV1Tokens() {
        val palette = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DARK)
        assertEquals(0xFF0B0D11.toInt(), palette.canvasArgb)
        assertEquals(0xC2181D26.toInt(), palette.surfaceArgb)
        assertEquals(0xFFF5F7FA.toInt(), palette.onSurfaceArgb)
        assertEquals(0xFFB0B7C3.toInt(), palette.onSurfaceMutedArgb)
        assertEquals(0x24E1E8F4, palette.lineArgb)
        assertNotEquals(
            GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT),
            palette
        )
    }
}
