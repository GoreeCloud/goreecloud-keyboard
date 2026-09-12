package com.goreecloud.keyboard

enum class KeyboardLayer {
    LETTERS,
    SYMBOLS,
    SYMBOLS_MORE,
    EMOJI,
}

enum class EmojiCategory(val label: String) {
    SMILEYS("☺"),
    PEOPLE("👤"),
    NATURE("🌿"),
    FOOD("🍽"),
    TRAVEL("✈"),
    SYMBOLS("★"),
}

object KeyboardLayout {
    private val englishNumberRow = keys("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    private val arabicNumberRow = keys("١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩", "٠")

    private val englishLetterRows = listOf(
        keys("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        keys("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        keys("z", "x", "c", "v", "b", "n", "m"),
    )

    // Display order intentionally follows the familiar Arabic PC/mobile key positions rather than
    // blindly mirroring QWERTY. KeyboardWritingDirection remains explicit metadata for platform and
    // accessibility work; this source tranche does not claim complete BiDi host/editor acceptance.
    private val arabicLetterRows = listOf(
        keys("ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "د"),
        keys("ش", "س", "ي", "ب", "ل", "ا", "ت", "ن", "م", "ك", "ط"),
        keys("ئ", "ء", "ؤ", "ر", "لا", "ى", "ة", "و", "ز", "ظ"),
    )

    private val symbolRows = listOf(
        englishNumberRow,
        keys("@", "#", "$", "%", "&", "-", "+", "(", ")"),
        keys("*", "\"", "'", ":", ";", "!", "?"),
    )
    private val moreSymbolRows = listOf(
        keys("[", "]", "{", "}", "<", ">", "=", "\\", "|"),
        keys("_", "^", "~", "`", "€", "£", "¥", "•", "…"),
        keys("±", "×", "÷", "§", "©", "®", "™", "—", "°"),
    )
    private val emojiRowsByCategory = mapOf(
        EmojiCategory.SMILEYS to listOf(
            keys("😀", "😃", "😄", "😁", "😆", "😅", "😂", "😊"),
            keys("🙂", "🥰", "😍", "🤩", "😎", "🤔", "😢", "😭"),
            keys("😡", "🥳", "😴", "🤗", "🙃", "😉", "😇", "🤭"),
        ),
        EmojiCategory.PEOPLE to listOf(
            keys("👍", "👍🏽", "👎", "👏", "🙌", "🙏🏾", "💪", "🤝"),
            keys("👋", "🫶", "👩‍💻", "👨‍💻", "🧑‍🚀", "👩‍🔬", "👨‍🔬", "🧑‍🍳"),
            keys("👨‍👩‍👧‍👦", "👩‍👩‍👦", "👨‍👨‍👧", "🧑‍🤝‍🧑", "🙋", "🙆", "🙅", "🤷"),
        ),
        EmojiCategory.NATURE to listOf(
            keys("🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼"),
            keys("🐨", "🐯", "🦁", "🐮", "🐷", "🐸", "🐵", "🦋"),
            keys("🌱", "🌿", "🍀", "🌵", "🌴", "🌻", "🌹", "🌊"),
        ),
        EmojiCategory.FOOD to listOf(
            keys("🍎", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🫐"),
            keys("🍒", "🍑", "🥭", "🍍", "🥑", "🥕", "🌽", "🥦"),
            keys("🍞", "🥐", "🧀", "🍕", "🍔", "🍜", "🍣", "🍰"),
        ),
        EmojiCategory.TRAVEL to listOf(
            keys("🚗", "🚕", "🚌", "🚎", "🏎️", "🚓", "🚑", "🚒"),
            keys("✈️", "🚀", "🚁", "🚲", "🛴", "🚆", "🚇", "🚢"),
            keys("⛵", "🏖️", "🏕️", "🏔️", "🗺️", "🧳", "🎡", "🏟️"),
        ),
        EmojiCategory.SYMBOLS to listOf(
            keys("❤️", "⭐", "✅", "❌", "🔥", "🎉", "🚀", "💯"),
            keys("✨", "⚠️", "💡", "📌", "🌈", "🏳️‍🌈", "🇺🇸", "🇨🇦"),
            keys("☀️", "🌙", "☁️", "⚡", "❄️", "☕", "🎵", "🎁"),
        ),
    )

    fun numberRow(language: KeyboardLanguage = KeyboardLanguage.ENGLISH_US): List<String> =
        when (language) {
            KeyboardLanguage.ENGLISH_US -> englishNumberRow
            KeyboardLanguage.ARABIC -> arabicNumberRow
        }

    fun characterRows(
        layer: KeyboardLayer,
        language: KeyboardLanguage = KeyboardLanguage.ENGLISH_US,
    ): List<List<String>> = when (layer) {
        KeyboardLayer.LETTERS -> when (language) {
            KeyboardLanguage.ENGLISH_US -> englishLetterRows
            KeyboardLanguage.ARABIC -> arabicLetterRows
        }
        KeyboardLayer.SYMBOLS -> symbolRows
        KeyboardLayer.SYMBOLS_MORE -> moreSymbolRows
        KeyboardLayer.EMOJI -> emojiRows(EmojiCategory.SMILEYS)
    }

    fun emojiRows(category: EmojiCategory): List<List<String>> =
        emojiRowsByCategory.getValue(category)

    fun nextEmojiCategory(category: EmojiCategory): EmojiCategory {
        val categories = EmojiCategory.entries
        return categories[(category.ordinal + 1) % categories.size]
    }

    private fun keys(vararg values: String): List<String> = values.toList()
}
