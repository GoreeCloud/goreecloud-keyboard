package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentStableMappedGeometryUsesGlazeUi21Values() {
        assertEquals(4f, GlazeKeyboardTokens.Space1Dp)
        assertEquals(8f, GlazeKeyboardTokens.Space2Dp)
        assertEquals(14f, GlazeKeyboardTokens.RadiusMediumDp)
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
    fun currentStableLightFoundationUsesCanonicalGlazeUi21Tokens() {
        val palette = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT)
        assertEquals(0xFFEEF3F9.toInt(), palette.canvasArgb)
        assertEquals(0xC2FFFFFF.toInt(), palette.surfaceArgb)
        assertEquals(0xFF172033.toInt(), palette.onSurfaceArgb)
        assertEquals(0xFF67748A.toInt(), palette.onSurfaceMutedArgb)
        assertEquals(0x295F7492, palette.lineArgb)
    }

    @Test
    fun currentStableDarkFoundationUsesCanonicalGlazeUi21Tokens() {
        val palette = GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.DARK)
        assertEquals(0xFF0D1119.toInt(), palette.canvasArgb)
        assertEquals(0xC719202D.toInt(), palette.surfaceArgb)
        assertEquals(0xFFF3F6FB.toInt(), palette.onSurfaceArgb)
        assertEquals(0xFFA1AEC0.toInt(), palette.onSurfaceMutedArgb)
        assertEquals(0x1FC1CFE5, palette.lineArgb)
        assertNotEquals(
            GlazeKeyboardTokens.palette(GlazeKeyboardTokens.Appearance.LIGHT),
            palette
        )
    }
}
