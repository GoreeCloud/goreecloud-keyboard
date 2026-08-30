package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EmojiRecentsTest {
    @Test
    fun duplicateIsPromotedWithoutDuplicationAndStringsStayIntact() {
        val recents = EmojiRecents(limit = 4, rowWidth = 2)
        recents.record("👩‍💻")
        recents.record("👍🏽")
        recents.record("👩‍💻")

        assertEquals(listOf("👩‍💻", "👍🏽"), recents.values())
    }

    @Test
    fun entriesAreBoundedAndBlankValuesAreIgnored() {
        val recents = EmojiRecents(limit = 3, rowWidth = 2)
        recents.record("😀")
        recents.record(" ")
        recents.record("😃")
        recents.record("😄")
        recents.record("😁")

        assertEquals(listOf("😁", "😄", "😃"), recents.values())
    }

    @Test
    fun rowsRemainBoundedAndPaddedForKeyboardLayout() {
        val recents = EmojiRecents(limit = 6, rowWidth = 2)
        recents.record("😀")
        recents.record("😃")
        recents.record("😄")

        assertEquals(
            listOf(listOf("😄", "😃"), listOf("😀"), emptyList<String>()),
            recents.rows(rowCount = 3),
        )
        assertTrue(recents.rows().size == EmojiRecents.DEFAULT_ROW_COUNT)
    }
}
