package com.goreecloud.keyboard

import java.util.Locale

enum class KeyboardWritingDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
}

enum class KeyboardLanguage(
    val primaryLanguageTag: String,
    val writingDirection: KeyboardWritingDirection,
    val supportsCaseShift: Boolean,
    val supportsLocalBootstrapSuggestions: Boolean,
    val spacebarLabel: String,
) {
    ENGLISH_US(
        primaryLanguageTag = "en-US",
        writingDirection = KeyboardWritingDirection.LEFT_TO_RIGHT,
        supportsCaseShift = true,
        supportsLocalBootstrapSuggestions = true,
        spacebarLabel = "English",
    ),
    ARABIC(
        primaryLanguageTag = "ar",
        writingDirection = KeyboardWritingDirection.RIGHT_TO_LEFT,
        supportsCaseShift = false,
        supportsLocalBootstrapSuggestions = false,
        spacebarLabel = "العربية",
    );

    companion object {
        /**
         * Resolve only from the active Android IME subtype. Device UI locale and editor text are
         * deliberately not language authority for the keyboard layout.
         */
        fun fromSubtypeLocale(localeValue: String?): KeyboardLanguage {
            val normalized = localeValue
                ?.trim()
                ?.replace('_', '-')
                ?.lowercase(Locale.ROOT)
                .orEmpty()
            return when (normalized.substringBefore('-')) {
                "ar" -> ARABIC
                else -> ENGLISH_US
            }
        }
    }
}
