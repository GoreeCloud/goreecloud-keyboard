package com.goreecloud.keyboard

/**
 * Local-only suggestion boundary for GoreeCloud Quill.
 *
 * The engine performs deterministic prefix matching and a deliberately bounded
 * one-edit correction pass. It exposes no network, persistence, telemetry, or
 * unrestricted language-model transport.
 */
class SuggestionEngine {
    fun suggest(prefix: String, dictionary: Collection<String>, limit: Int = 3): List<String> {
        if (prefix.isBlank() || limit <= 0) return emptyList()

        val normalized = prefix.lowercase()
        val candidates = dictionary
            .asSequence()
            .filter { it.isNotBlank() }
            .distinctBy { it.lowercase() }
            .toList()

        val prefixMatches = candidates
            .asSequence()
            .filter { it.startsWith(normalized, ignoreCase = true) }
            .sortedWith(compareBy<String> { it.length }.thenBy(String.CASE_INSENSITIVE_ORDER) { it })
            .toList()

        if (prefixMatches.size >= limit || normalized.length < MIN_CORRECTION_LENGTH) {
            return prefixMatches.take(limit)
        }

        val corrections = candidates
            .asSequence()
            .filterNot { it.startsWith(normalized, ignoreCase = true) }
            .filter { isSingleEditAway(normalized, it.lowercase()) }
            .sortedWith(
                compareBy<String> { kotlin.math.abs(it.length - normalized.length) }
                    .thenBy { it.length }
                    .thenBy(String.CASE_INSENSITIVE_ORDER) { it }
            )
            .toList()

        return (prefixMatches + corrections).take(limit)
    }

    private fun isSingleEditAway(left: String, right: String): Boolean {
        val lengthDifference = kotlin.math.abs(left.length - right.length)
        if (lengthDifference > 1) return false

        if (left.length == right.length) {
            val mismatches = left.indices.filter { left[it] != right[it] }
            return when (mismatches.size) {
                1 -> true
                2 -> {
                    val first = mismatches[0]
                    val second = mismatches[1]
                    second == first + 1 &&
                        left[first] == right[second] &&
                        left[second] == right[first]
                }
                else -> false
            }
        }

        val shorter = if (left.length < right.length) left else right
        val longer = if (left.length < right.length) right else left
        var shortIndex = 0
        var longIndex = 0
        var skipped = false

        while (shortIndex < shorter.length && longIndex < longer.length) {
            if (shorter[shortIndex] == longer[longIndex]) {
                shortIndex++
                longIndex++
            } else {
                if (skipped) return false
                skipped = true
                longIndex++
            }
        }
        return true
    }

    private companion object {
        const val MIN_CORRECTION_LENGTH = 3
    }
}
