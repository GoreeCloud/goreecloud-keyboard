package com.goreecloud.keyboard

/**
 * Non-semantic GLAZE UI V1.4 atmosphere/material boundary for GoreeCloud
 * Keyboard.
 *
 * Governing material rule: Neutral glass is the material. Color is an accent.
 * KeyboardView consumes the neutral material palette from [GlazeKeyboardTokens].
 * [GlazeKeyboardOptics] adds the bounded V1.4 optical resolver while keeping the
 * IME stricter than ordinary application surfaces.
 *
 * Keyboard never uses editor/content sampling, suggestion text, clipboard state,
 * application identity, key history, emoji history, remote color derivation,
 * persistent sample history, semantic inference, telemetry, or network lookup to
 * drive optical presentation.
 */
internal object GlazeKeyboardAtmosphere {
    const val DefaultMaterialTintContribution = 0f

    const val TealAsBaseMaterialAllowed = false
    const val GreenAsBaseMaterialAllowed = false
    const val AquaAsBaseMaterialAllowed = false
    const val AmberAsBaseMaterialAllowed = false
    const val BrandColorMayDefineSubstrate = false
    const val SemanticColorMayDefineSubstrate = false

    // Keyboard intentionally declines decorative environment tinting/memory even
    // though the generic V1.4 engine permits a bounded form on less-sensitive UI.
    const val EnvironmentalAuraOptional = false
    const val EnvironmentalAuraMayPassThroughBackdrop = false
    const val EnvironmentalAuraMustRemainOutsideSubstrate = true
    const val EnvironmentalColorMemoryEnabled = false
    const val EnvironmentalColorMemoryMaxInfluence = 0f

    const val EditorContentSamplingAllowed = false
    const val SuggestionContentSamplingAllowed = false
    const val ClipboardSamplingAllowed = false
    const val ApplicationIdentitySamplingAllowed = false
    const val RemoteColorDerivationAllowed = false
    const val PersistentSampleHistoryAllowed = false
    const val SemanticInferenceAllowed = false
    const val AnimatedAtmosphereEnabled = false
}
