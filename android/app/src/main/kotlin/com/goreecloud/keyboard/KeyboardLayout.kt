package com.goreecloud.keyboard

enum class KeyboardLayer {
    LETTERS,
    SYMBOLS,
    SYMBOLS_MORE
}

object KeyboardLayout {
    private val letterRows = listOf(
        "qwertyuiop".toList(),
        "asdfghjkl".toList(),
        "zxcvbnm".toList()
    )

    private val symbolRows = listOf(
        "1234567890".toList(),
        "@#\$%&*-+()".toList(),
        listOf('.', ',', '?', '!', '\'', '"', ':', ';', '/')
    )

    private val moreSymbolRows = listOf(
        listOf('[', ']', '{', '}', '<', '>', '^', '_', '=', '+'),
        listOf('\\', '|', '~', '`', '€', '£', '¥', '•'),
        listOf('…', '–', '—', '°', '©', '®', '™')
    )

    fun characterRows(layer: KeyboardLayer): List<List<Char>> = when (layer) {
        KeyboardLayer.LETTERS -> letterRows
        KeyboardLayer.SYMBOLS -> symbolRows
        KeyboardLayer.SYMBOLS_MORE -> moreSymbolRows
    }
}
