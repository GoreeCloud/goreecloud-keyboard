package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Test

class SuggestionEngineTest {
    @Test
    fun returnsBoundedPrefixMatches() {
        val engine = SuggestionEngine()
        val result = engine.suggest(
            prefix = "go",
            dictionary = listOf("goreecloud", "good", "goal", "garden"),
            limit = 2
        )

        assertEquals(listOf("goal", "good"), result)
    }

    @Test
    fun addsSingleSubstitutionCorrection() {
        val engine = SuggestionEngine()
        val result = engine.suggest(
            prefix = "hellp",
            dictionary = listOf("hello", "hero", "world"),
            limit = 3
        )

        assertEquals(listOf("hello"), result)
    }

    @Test
    fun recognizesAdjacentTranspositionLocally() {
        val engine = SuggestionEngine()
        val result = engine.suggest(
            prefix = "teh",
            dictionary = listOf("the", "then", "them"),
            limit = 3
        )

        assertEquals(listOf("the"), result)
    }

    @Test
    fun doesNotRunCorrectionPassForVeryShortInput() {
        val engine = SuggestionEngine()
        val result = engine.suggest(
            prefix = "gi",
            dictionary = listOf("go", "hi", "git"),
            limit = 3
        )

        assertEquals(listOf("git"), result)
    }

    @Test
    fun rejectsCorrectionsMoreThanOneEditAway() {
        val engine = SuggestionEngine()
        val result = engine.suggest(
            prefix = "cloud",
            dictionary = listOf("clown", "could", "goreecloud"),
            limit = 3
        )

        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun returnsNothingForNonPositiveLimit() {
        val engine = SuggestionEngine()
        assertEquals(emptyList<String>(), engine.suggest("go", listOf("good"), limit = 0))
    }
}
