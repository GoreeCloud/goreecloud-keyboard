package com.goreecloud.keyboard

/**
 * Bounded native mapping of the GLAZE UI V1.1 foundation and appearance subset consumed
 * by GoreeCloud Keyboard's first-party Android surface.
 *
 * Current authority is machine version 1.1.0 at exact Stable release revision
 * 15cc76d2bcd4065552dc31c77145b63f34d9e7b2. V1.1 preserves the V1 structural,
 * semantic, accessibility, component, and performance contracts while adding the
 * optical-refinement and atmospheric contracts.
 *
 * The existing [RadiusMediumDp] property name remains a source-compatible alias for the
 * inherited V1 12 dp small/control structural radius. V1.1 optical geometry references
 * are recorded separately and must not silently replace structural radii or interaction
 * hit targets.
 *
 * Android night mode remains a binary Light/Dark signal in KeyboardView. Deep Dark is
 * defined here from the exact V1.1 structural appearance contract but is not inferred
 * from ordinary Android dark mode and is not auto-selected by the current IME runtime.
 *
 * This is Development source mapping only; rendered/accessibility/device/release
 * acceptance remains separately required.
 */
internal object GlazeKeyboardTokens {
    const val TargetVersion = "1.1.0"
    const val SourceRevision = "15cc76d2bcd4065552dc31c77145b63f34d9e7b2"

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

    // V1.1 optical geometry references; these do not redefine structural radii.
    const val OpticalMicroDp = 8f
    const val OpticalControlDp = 16f
    const val OpticalContainerDp = 24f
    const val OpticalHeroDp = 32f
    const val OpticalCapsuleDp = 999f

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

    val DeepDarkPalette = Palette(
        canvasArgb = 0xFF05070A.toInt(),
        surfaceArgb = 0xCC12161D.toInt(),
        onSurfaceArgb = 0xFFF5F7FA.toInt(),
        onSurfaceMutedArgb = 0xFFABB4C2.toInt(),
        lineArgb = 0x26E1E8F4,
    )

    fun interactionFloorDp(touchAssistance: Boolean): Float =
        if (touchAssistance) TouchAssistanceInteractionFloorDp else GeneralInteractionFloorDp

    fun palette(appearance: Appearance): Palette = when (appearance) {
        Appearance.LIGHT -> LightPalette
        Appearance.DARK -> DarkPalette
        Appearance.DEEP_DARK -> DeepDarkPalette
    }
}
