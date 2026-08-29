package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KeyboardLayoutTest {
    @Test
    fun lettersExposeExpectedQwertyRows() {
        val rows = KeyboardLayout.characterRows(KeyboardLayer.LETTERS)

        assertEquals("qwertyuiop", rows[0].joinToString(""))
        assertEquals("asdfghjkl", rows[1].joinToString(""))
        assertEquals("zxcvbnm", rows[2].joinToString(""))
    }

    @Test
    fun primarySymbolsExposeDigitsAndCommonPunctuationWithoutLetters() {
        val rows = KeyboardLayout.characterRows(KeyboardLayer.SYMBOLS)
        val characters = rows.flatten()

        assertEquals("1234567890", rows[0].joinToString(""))
        assertTrue(characters.contains('@'))
        assertTrue(characters.contains('?'))
        assertTrue(characters.contains('!'))
        assertFalse(characters.any { it.isLetter() })
    }

    @Test
    fun secondarySymbolsExposeBracketsOperatorsCurrencyAndTypographyWithoutLetters() {
        val rows = KeyboardLayout.characterRows(KeyboardLayer.SYMBOLS_MORE)
        val characters = rows.flatten()

        for (character in listOf('[', ']', '{', '}', '<', '>', '=', '\\', '|', '~', '€', '£', '¥', '…', '—', '°')) {
            assertTrue("expected secondary symbol $character", characters.contains(character))
        }
        assertFalse(characters.any { it.isLetter() })
    }

    @Test
    fun everyLayerReturnsThreeCharacterRows() {
        for (layer in KeyboardLayer.entries) {
            assertEquals("$layer must expose three character rows", 3, KeyboardLayout.characterRows(layer).size)
        }
    }
}
