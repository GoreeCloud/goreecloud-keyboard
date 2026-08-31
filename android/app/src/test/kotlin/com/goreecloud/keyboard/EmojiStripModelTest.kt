package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmojiStripModelTest {
    @Test
    fun categoryOnlyStripUsesCompactVisibleLabelsAndCompleteAccessibleNames() {
        val entries = EmojiStripModel.entries(hasRecents = false)

        assertEquals(EmojiCategory.entries.size, entries.size)
        assertEquals(EmojiCategory.entries.toList(), entries.mapNotNull { it.category })
        assertTrue(entries.all { it.visibleLabel.length <= 2 })
        assertTrue(entries.all { it.accessibilityLabel.endsWith(" emoji") })
        assertFalse(entries.any { it.recent || it.clearRecents })
    }

    @Test
    fun recentControlsPrecedeCategoriesOnlyWhenRecentsExist() {
        val entries = EmojiStripModel.entries(hasRecents = true)

        assertEquals(EmojiCategory.entries.size + 2, entries.size)
        assertTrue(entries[0].recent)
        assertEquals("Recent emoji", entries[0].accessibilityLabel)
        assertTrue(entries[1].clearRecents)
        assertEquals("Clear recent emoji", entries[1].accessibilityLabel)
        assertEquals(EmojiCategory.entries.toList(), entries.drop(2).mapNotNull { it.category })
    }
}
