package com.goreecloud.keyboard

/**
 * Non-semantic GLAZE UI V1.1 atmospheric source contract for GoreeCloud Keyboard.
 *
 * These values are deliberately not consumed by KeyboardView in this Development slice.
 * The IME must remain certainty-first and readable while typing, and no atmosphere may
 * represent selection, focus, sensitive-editor state, privacy, security, identity,
 * recovery, availability, or any other authoritative meaning.
 *
 * Environmental Color Memory is not enabled: no editor/content sampling, remote color
 * derivation, persistent sample history, semantic inference, or animated atmosphere is
 * authorized by this mapping.
 */
internal object GlazeKeyboardAtmosphere {
    const val DeepTealArgb = 0xFF0F6B6F.toInt()
    const val MineralTealArgb = 0xFF1C8A8D.toInt()
    const val SoftAquaArgb = 0xFF8FD6D2.toInt()
    const val SoftAmberArgb = 0xFFD9A35F.toInt()
    const val ChampagneGoldArgb = 0xFFE7C78A.toInt()
    const val WarmGlowArgb = 0xFFF2D7A6.toInt()

    const val LightTealAuraMaxAlpha = 0.08f
    const val LightAmberAuraMaxAlpha = 0.04f
    const val DarkTealAuraMaxAlpha = 0.12f
    const val DarkAmberAuraMaxAlpha = 0.06f
    const val DeepDarkTealAuraMaxAlpha = 0.16f
    const val DeepDarkAmberAuraMaxAlpha = 0.08f

    const val EnvironmentalColorMemoryEnabled = false
    const val RemoteColorDerivationAllowed = false
    const val PersistentSampleHistoryAllowed = false
    const val SemanticInferenceAllowed = false
}
