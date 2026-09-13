package com.goreecloud.keyboard

import kotlin.math.roundToInt

/**
 * Bounded native mapping of the current GLAZE UI V1.4 foundation, inherited
 * Frosted Neutral material, appearance, geometry, target-size, and interaction-
 * state subset consumed by GoreeCloud Keyboard's first-party Android surface.
 *
 * Current authority is machine version 1.4.0 at exact merged Stable source
 * revision 84cb3db4884042f0fa25ed6d475a127fb110f596. V1.4 is additive over the
 * existing neutral material contract and introduces Optical Intelligence through
 * the separate [GlazeKeyboardOptics] resolver.
 *
 * Governing rule: Neutral glass is the material. Color is an accent.
 *
 * Keyboard is a highly sensitive input surface. Typed/editor content, suggestions,
 * clipboard state, application identity, and surrounding text are never visual-
 * context inputs to the Glaze mapping.
 *
 * The existing [RadiusMediumDp] property name remains a source-compatible alias
 * for the 12 dp control role. Optical geometry references remain separate from
 * structural radii and interaction hit targets.
 */
internal object GlazeKeyboardTokens {
    const val TargetVersion = "1.4.0"
    const val SourceRevision = "84cb3db4884042f0fa25ed6d475a127fb110f596"

    enum class Appearance { LIGHT, DARK, DEEP_DARK }

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

    const val OpticalMicroDp = 8f
    const val OpticalControlDp = 16f
    const val OpticalContainerDp = 24f
    const val OpticalHeroDp = 32f
    const val OpticalCapsuleDp = 999f

    const val PressedOverlayOpacity = 0.095f
    const val SelectedOverlayOpacity = 0.12f
    const val FocusWidthDp = 3f
    const val IncreasedContrastFocusWidthDp = 4f

    val LightPalette = Palette(
        canvasArgb = 0xFFF5F7FA.toInt(),
        surfaceArgb = 0x94FFFFFF.toInt(),
        onSurfaceArgb = 0xFF151A23.toInt(),
        onSurfaceMutedArgb = 0xFF5D6675.toInt(),
        lineArgb = 0x1A505050,
    )

    val DarkPalette = Palette(
        canvasArgb = 0xFF0B0D11.toInt(),
        surfaceArgb = 0x9E19191B.toInt(),
        onSurfaceArgb = 0xFFF5F7FA.toInt(),
        onSurfaceMutedArgb = 0xFFB0B7C3.toInt(),
        lineArgb = 0x1AFFFFFF,
    )

    val DeepDarkPalette = Palette(
        canvasArgb = 0xFF05070A.toInt(),
        surfaceArgb = 0xB80E0E10.toInt(),
        onSurfaceArgb = 0xFFF5F7FA.toInt(),
        onSurfaceMutedArgb = 0xFFABB4C2.toInt(),
        lineArgb = 0x17FFFFFF,
    )

    fun interactionFloorDp(touchAssistance: Boolean): Float =
        if (touchAssistance) TouchAssistanceInteractionFloorDp else GeneralInteractionFloorDp

    fun palette(appearance: Appearance): Palette = when (appearance) {
        Appearance.LIGHT -> LightPalette
        Appearance.DARK -> DarkPalette
        Appearance.DEEP_DARK -> DeepDarkPalette
    }

    fun stateOverlayArgb(appearance: Appearance, opacity: Float): Int {
        val boundedOpacity = opacity.coerceIn(0f, 1f)
        val alpha = (boundedOpacity * 255f).roundToInt()
        return (alpha shl 24) or (palette(appearance).onSurfaceArgb and 0x00FFFFFF)
    }
}
