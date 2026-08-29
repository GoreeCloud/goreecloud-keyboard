package com.goreecloud.keyboard

enum class KeyboardLayer {
    LETTERS,
    SYMBOLS,
    SYMBOLS_MORE,
    EMOJI,
}

object KeyboardLayout {
    private val letterRows = listOf(
        keys("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        keys("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        keys("z", "x", "c", "v", "b", "n", "m"),
    )
    private val symbolRows = listOf(
        keys("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        keys("@", "#", "$", "%", "&", "-", "+", "(", ")"),
        keys("*", "\"", "'", ":", ";", "!", "?"),
    )
    private val moreSymbolRows = listOf(
        keys("[", "]", "{", "}", "<", ">", "=", "\\", "|"),
        keys("_", "^", "~", "`", "€", "£", "¥", "•", "…"),
        keys("±", "×", "÷", "§", "©", "®", "™", "—", "°"),
    )
    private val emojiRows = listOf(
        keys("😀", "😃", "😄", "😁", "😆", "😅", "😂", "😊"),
        keys("🙂", "🙃", "😉", "😍", "😘", "😎", "🤔", "🤗"),
        keys("👍", "👏", "🙏", "🎉", "🔥", "⭐", "✅", "🚀"),
    )

    fun characterRows(layer: KeyboardLayer): List<List<String>> = when (layer) {
        KeyboardLayer.LETTERS -> letterRows
        KeyboardLayer.SYMBOLS -> symbolRows
        KeyboardLayer.SYMBOLS_MORE -> moreSymbolRows
        KeyboardLayer.EMOJI -> emojiRows
    }

    private fun keys(vararg values: String): List<String> = values.toList()
}
