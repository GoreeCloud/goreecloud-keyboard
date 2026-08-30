package com.goreecloud.keyboard

/**
 * Process-memory-only recency model for emoji committed from the keyboard surface.
 *
 * Nothing is persisted, synchronized, logged, or transmitted. Restarting the IME process clears
 * the list. Exact String values are retained so multi-code-point emoji remain intact.
 */
class EmojiRecents(
    private val limit: Int = DEFAULT_LIMIT,
    private val rowWidth: Int = DEFAULT_ROW_WIDTH,
) {
    private val entries = mutableListOf<String>()

    init {
        require(limit > 0) { "emoji recent limit must be positive" }
        require(rowWidth > 0) { "emoji recent row width must be positive" }
    }

    fun record(value: String) {
        if (value.isBlank()) return
        entries.remove(value)
        entries.add(0, value)
        while (entries.size > limit) entries.removeAt(entries.lastIndex)
    }

    fun clear() {
        entries.clear()
    }

    fun values(): List<String> = entries.toList()

    fun rows(rowCount: Int = DEFAULT_ROW_COUNT): List<List<String>> {
        require(rowCount > 0) { "emoji recent row count must be positive" }
        val visible = entries.take(rowWidth * rowCount).chunked(rowWidth).toMutableList()
        while (visible.size < rowCount) visible.add(emptyList())
        return visible
    }

    companion object {
        const val DEFAULT_LIMIT = 24
        const val DEFAULT_ROW_WIDTH = 8
        const val DEFAULT_ROW_COUNT = 3
    }
}
