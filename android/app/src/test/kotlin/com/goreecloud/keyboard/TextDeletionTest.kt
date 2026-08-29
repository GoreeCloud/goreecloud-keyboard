package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Test

class TextDeletionTest {
    @Test
    fun emptyInputDeletesNothing() {
        assertEquals(0, TextDeletion.previousTextUnitCodePointCount(""))
    }

    @Test
    fun plainSupplementaryEmojiDeletesOneCodePoint() {
        assertEquals(1, TextDeletion.previousTextUnitCodePointCount("hello😀"))
    }

    @Test
    fun emojiModifierStaysWithItsBase() {
        assertEquals(2, TextDeletion.previousTextUnitCodePointCount("ok👍🏽"))
    }

    @Test
    fun zwjEmojiSequenceDeletesAsOneBoundedTextUnit() {
        assertEquals(3, TextDeletion.previousTextUnitCodePointCount("work👩‍💻"))
        assertEquals(4, TextDeletion.previousTextUnitCodePointCount("work👩🏽‍💻"))
    }

    @Test
    fun variationSelectorAndKeycapStayWithTheirBase() {
        assertEquals(2, TextDeletion.previousTextUnitCodePointCount("love❤️"))
        assertEquals(3, TextDeletion.previousTextUnitCodePointCount("press1️⃣"))
    }

    @Test
    fun combiningMarkStaysWithItsBase() {
        assertEquals(2, TextDeletion.previousTextUnitCodePointCount("café"))
    }

    @Test
    fun regionalIndicatorPairDeletesAsOneFlagUnit() {
        assertEquals(2, TextDeletion.previousTextUnitCodePointCount("flag🇺🇸"))
    }

    @Test
    fun crlfDeletesAsOneLineBreakUnit() {
        assertEquals(2, TextDeletion.previousTextUnitCodePointCount("line\r\n"))
    }
}
