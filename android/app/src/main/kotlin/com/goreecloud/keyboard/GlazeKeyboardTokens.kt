package com.goreecloud.keyboard

import kotlin.math.roundToInt

/**
 * Bounded native mapping of the current GLAZE UI V1.3 Adaptive Resonance foundation
 * consumed by GoreeCloud Keyboard's first-party Android surface.
 *
 * V1.3 inherits the V1.2 Frosted Neutral material, appearance, geometry, target-size,
 * and interaction-state foundation while adding bounded adaptive expression,
 * ergonomic composition, and resilience requirements. Current authority is machine
 * version 1.3.0 at exact Stable integration revision
 * fc7cc91d2eace8da2371371c2855c24cbcb326a1.
 *
 * Governing rule: Neutral glass is the material. Color is an accent.
 *
 * Android night mode remains a binary Light/Dark signal in KeyboardView. Deep Dark is
 * defined here but is not inferred from ordinary Android dark mode. Environmental
 * adaptation remains disabled unless separately implemented and accepted; the IME must
 * never derive material color from typed/editor content.
 *
 * This mapping does not by itself establish complete rendered, accessibility,
 * representative-device, rollback, release, or production acceptance.
 */
internal object GlazeKeyboardTokens {
    const val TargetVersion = "1.3.0"
    const val ReleaseTheme = "Adaptive Resonance"
    const val SourceRevision = "fc7cc91d2eace8da2371371c2855c24cbcb326a1"
    const val OpticalContract = "tokens/glaze-v1.2-optical-foundation.candidate.json"
    const val AdaptiveContract = "contracts/v1.3/adaptive-resonance.plan.json"
    const val StableWebEntrypoint = "css/glaze-v1.3.0.css"
    const val StableRuntimeEntrypoint = "js/glaze-v1.3.0.mjs"
    const val RollbackBaselineVersion = "1.2.0"

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

    // V1.3 inherits these V1.2 optical geometry references; they remain separate from
    // structural radii and interaction hit targets.
    const val OpticalMicroDp = 8f
    const val OpticalControlDp = 16f
    const val OpticalContainerDp = 24f
    const val OpticalHeroDp = 32f
    const val OpticalCapsuleDp = 999f

    // Inherited interaction-state calibration remains deterministic in the IME.
    const val PressedOverlayOpacity = 0.095f
    const val SelectedOverlayOpacity = 0.12f
    const val FocusWidthDp = 3f
    const val IncreasedContrastFocusWidthDp = 4f

    /**
     * Frosted Neutral base-glass mapping inherited by V1.3. Canvas/text roles remain
     * structural while the interactive key substrate stays neutral rather than becoming
     * semantic, brand, or environmentally sampled color.
     */
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
