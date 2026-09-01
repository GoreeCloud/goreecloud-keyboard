package com.goreecloud.keyboard

/**
 * Bounded native mapping of the current Glaze UI 2.1 Stable token map consumed by
 * GoreeCloud Keyboard's first-party Android surface.
 *
 * The 2.1 migration preserves compatible Light/Dark, spacing, radius, and general-target
 * semantics from the prior 2.0 mapping while adding the canonical Touch Assistance floor.
 * Deep Dark and the broader accessibility/material-resolution renderings remain separate
 * application gates until Keyboard has concrete platform wiring and acceptance evidence.
 */
internal object GlazeKeyboardTokens {
    enum class Appearance { LIGHT, DARK }

    data class Palette(
        val canvasArgb: Int,
        val surfaceArgb: Int,
        val onSurfaceArgb: Int,
        val onSurfaceMutedArgb: Int,
        val lineArgb: Int,
    )

    const val Space1Dp = 4f
    const val Space2Dp = 8f
    const val RadiusMediumDp = 14f
    const val GeneralInteractionFloorDp = 48f
    const val TouchAssistanceInteractionFloorDp = 56f
    const val SuggestionStripHeightDp = GeneralInteractionFloorDp

    val LightPalette = Palette(
        canvasArgb = 0xFFEEF3F9.toInt(),
        surfaceArgb = 0xC2FFFFFF.toInt(),
        onSurfaceArgb = 0xFF172033.toInt(),
        onSurfaceMutedArgb = 0xFF67748A.toInt(),
        lineArgb = 0x295F7492,
    )

    val DarkPalette = Palette(
        canvasArgb = 0xFF0D1119.toInt(),
        surfaceArgb = 0xC719202D.toInt(),
        onSurfaceArgb = 0xFFF3F6FB.toInt(),
        onSurfaceMutedArgb = 0xFFA1AEC0.toInt(),
        lineArgb = 0x1FC1CFE5,
    )

    fun interactionFloorDp(touchAssistance: Boolean): Float =
        if (touchAssistance) TouchAssistanceInteractionFloorDp else GeneralInteractionFloorDp

    fun palette(appearance: Appearance): Palette = when (appearance) {
        Appearance.LIGHT -> LightPalette
        Appearance.DARK -> DarkPalette
    }
}
