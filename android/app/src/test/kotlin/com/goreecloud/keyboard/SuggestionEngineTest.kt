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
}
