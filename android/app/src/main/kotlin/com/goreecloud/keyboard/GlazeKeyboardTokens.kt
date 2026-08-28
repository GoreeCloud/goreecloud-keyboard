package com.goreecloud.keyboard

/**
 * Bounded native mapping of the Glaze UI 2.0 Stable semantics currently consumed by
 * GoreeCloud Keyboard's first-party Android surface.
 *
 * The keyboard intentionally maps only semantics it implements today. Deep Dark,
 * expression modes, complete material-role behavior, and full accessibility/device
 * acceptance remain separate application gates.
 */
internal object GlazeKeyboardTokens {
    const val Space1Dp = 4f
    const val Space2Dp = 8f
    const val RadiusMediumDp = 12f
    const val GeneralInteractionFloorDp = 48f
    const val SuggestionStripHeightDp = GeneralInteractionFloorDp

    val LightCanvasArgb: Int = 0xFFEEF3F9.toInt()
    val LightSurfaceArgb: Int = 0xC2FFFFFF.toInt()
    val LightOnSurfaceArgb: Int = 0xFF172033.toInt()
    val LightOnSurfaceMutedArgb: Int = 0xFF67748A.toInt()
}
