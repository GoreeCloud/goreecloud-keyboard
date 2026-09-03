package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KeyboardPortablePreferenceTransferTest {
    @Test
    fun exportBytesContainOnlyTheApprovedCategorySnapshot() {
        val bytes = KeyboardPortablePreferenceTransfer.exportBytes(
            EmojiCategoryPreferenceReader { EmojiCategory.NATURE }
        )
        val encoded = bytes.toString(Charsets.UTF_8)

        assertTrue(encoded.contains("emoji_category=NATURE"))
        assertFalse(encoded.contains("emoji_recents"))
        assertFalse(encoded.contains("typed_text"))
        assertFalse(encoded.contains("search_query"))
        assertFalse(encoded.contains("clipboard"))
    }

    @Test
    fun validBytesApplyExactlyOneCategoryWrite() {
        val writes = mutableListOf<EmojiCategory>()
        val bytes = KeyboardPortablePreferenceTransfer.exportBytes(
            EmojiCategoryPreferenceReader { EmojiCategory.FOOD }
        )

        val result = KeyboardPortablePreferenceTransfer.importBytes(
            bytes,
            EmojiCategoryPreferenceWriter { writes += it },
        )

        assertTrue(result is KeyboardPortablePreferenceTransfer.ImportResult.Applied)
        assertEquals(listOf(EmojiCategory.FOOD), writes)
    }

    @Test
    fun oversizedFileIsRejectedBeforeAnyPreferenceWrite() {
        val writes = mutableListOf<EmojiCategory>()

        val result = KeyboardPortablePreferenceTransfer.importBytes(
            ByteArray(KeyboardPortablePreferenceTransfer.MAX_IMPORT_BYTES + 1) { 'x'.code.toByte() },
            EmojiCategoryPreferenceWriter { writes += it },
        )

        assertTrue(result is KeyboardPortablePreferenceTransfer.ImportResult.Rejected)
        assertTrue(writes.isEmpty())
    }

    @Test
    fun malformedUtf8IsRejectedBeforeAnyPreferenceWrite() {
        val writes = mutableListOf<EmojiCategory>()

        val result = KeyboardPortablePreferenceTransfer.importBytes(
            byteArrayOf(0xC3.toByte(), 0x28),
            EmojiCategoryPreferenceWriter { writes += it },
        )

        assertTrue(result is KeyboardPortablePreferenceTransfer.ImportResult.Rejected)
        assertTrue(writes.isEmpty())
    }

    @Test
    fun tamperedPortablePayloadIsRejectedBeforeAnyPreferenceWrite() {
        val writes = mutableListOf<EmojiCategory>()
        val bytes = KeyboardPortablePreferenceTransfer.exportBytes(
            EmojiCategoryPreferenceReader { EmojiCategory.SYMBOLS }
        )
        val tampered = bytes.toString(Charsets.UTF_8)
            .replace("emoji_category=SYMBOLS", "emoji_category=SMILEYS")
            .toByteArray(Charsets.UTF_8)

        val result = KeyboardPortablePreferenceTransfer.importBytes(
            tampered,
            EmojiCategoryPreferenceWriter { writes += it },
        )

        assertTrue(result is KeyboardPortablePreferenceTransfer.ImportResult.Rejected)
        assertTrue(writes.isEmpty())
    }
}
