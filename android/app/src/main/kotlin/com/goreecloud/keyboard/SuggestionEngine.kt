package com.goreecloud.keyboard

/**
 * Local-only suggestion boundary for GoreeCloud Quill.
 *
 * The foundation deliberately exposes no network transport. Future language models,
 * dictionaries, personalization, and correction engines must plug into this boundary
 * without granting the keyboard unrestricted network access.
 */
class SuggestionEngine {
    fun suggest(prefix: String, dictionary: Collection<String>, limit: Int = 3): List<String> {
        if (prefix.isBlank()) return emptyList()
        val normalized = prefix.lowercase()
        return dictionary.asSequence()
            .filter { it.startsWith(normalized, ignoreCase = true) }
            .sortedWith(compareBy<String> { it.length }.thenBy { it })
            .take(limit)
            .toList()
    }
}
