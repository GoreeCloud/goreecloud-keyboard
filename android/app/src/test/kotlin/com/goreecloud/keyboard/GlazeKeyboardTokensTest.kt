package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Test

class GlazeKeyboardTokensTest {
    @Test
    fun currentStableMappedGeometryUsesGlazeUi2Values() {
        assertEquals(4f, GlazeKeyboardTokens.Space1Dp)
        assertEquals(8f, GlazeKeyboardTokens.Space2Dp)
        assertEquals(12f, GlazeKeyboardTokens.RadiusMediumDp)
        assertEquals(48f, GlazeKeyboardTokens.GeneralInteractionFloorDp)
        assertEquals(
            GlazeKeyboardTokens.GeneralInteractionFloorDp,
            GlazeKeyboardTokens.SuggestionStripHeightDp
        )
    }

    @Test
    fun currentStableLightFoundationUsesReviewedGlazeUi2Tokens() {
        assertEquals(0xFFEEF3F9.toInt(), GlazeKeyboardTokens.LightCanvasArgb)
        assertEquals(0xC2FFFFFF.toInt(), GlazeKeyboardTokens.LightSurfaceArgb)
        assertEquals(0xFF172033.toInt(), GlazeKeyboardTokens.LightOnSurfaceArgb)
        assertEquals(0xFF67748A.toInt(), GlazeKeyboardTokens.LightOnSurfaceMutedArgb)
    }
}
