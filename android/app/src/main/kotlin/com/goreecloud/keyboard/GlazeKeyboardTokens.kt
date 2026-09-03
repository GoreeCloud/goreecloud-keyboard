package com.goreecloud.keyboard

/**
 * Bounded native mapping of the official GLAZE UI V1.0 foundation subset consumed by
 * GoreeCloud Keyboard's first-party Android surface.
 *
 * Current authority is machine version 1.0.0 at exact canonical source revision
 * 70909bbdccad378fb7281ae1842e2f5beed64c38. This source mapping is Development
 * evidence only and does not establish rendered, accessibility, representative-device,
 * release, or production acceptance.
 *
 * The existing `RadiusMediumDp` property name is retained for source compatibility with
 * KeyboardView, but its value maps to the V1 12 dp small/control foundation radius. V1
 * also requires 48 dp normal touch-oriented targets and 56 dp Touch Assistance/far-view
 * targets where applicable.
 *
 * V1 publishes Light, Dark, and Deep Dark. Keyboard currently maps Light/Dark only;
 * Deep Dark remains a separate implementation and application-acceptance gate.
 */
internal object GlazeKeyboardTokens {
    const val TargetVersion = "1.0.0"
    const val SourceRevision = "70909bbdccad378fb7281ae1842e2f5beed64c38"

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
    const val RadiusMediumDp = 12f
    const val GeneralInteractionFloorDp = 48f
    const val TouchAssistanceInteractionFloorDp = 56f
    const val SuggestionStripHeightDp = GeneralInteractionFloorDp

    val LightPalette = Palette(
        canvasArgb = 0xFFF5F7FA.toInt(),
        surfaceArgb = 0xC7FFFFFF.toInt(),
        onSurfaceArgb = 0xFF151A23.toInt(),
        onSurfaceMutedArgb = 0xFF5D6675.toInt(),
        lineArgb = 0x24192332,
    )

    val DarkPalette = Palette(
        canvasArgb = 0xFF0B0D11.toInt(),
        surfaceArgb = 0xC2181D26.toInt(),
        onSurfaceArgb = 0xFFF5F7FA.toInt(),
        onSurfaceMutedArgb = 0xFFB0B7C3.toInt(),
        lineArgb = 0x24E1E8F4,
    )

    fun interactionFloorDp(touchAssistance: Boolean): Float =
        if (touchAssistance) TouchAssistanceInteractionFloorDp else GeneralInteractionFloorDp

    fun palette(appearance: Appearance): Palette = when (appearance) {
        Appearance.LIGHT -> LightPalette
        Appearance.DARK -> DarkPalette
    }
}
