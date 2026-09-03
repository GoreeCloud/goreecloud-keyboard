package com.goreecloud.keyboard

/**
 * File-transfer policy for the current one-field portable Keyboard preference format.
 *
 * This helper never reads editor state, emoji recents, clipboard contents, search history,
 * telemetry, Identity data, or credentials. It only bridges explicit user-selected file bytes to
 * the already-reviewed category-only export/import seams.
 */
object KeyboardPortablePreferenceTransfer {
    const val MIME_TYPE = "text/plain"
    const val EXPORT_FILE_NAME = "goreecloud-keyboard-preferences.txt"
    const val MAX_IMPORT_BYTES = 4096

    sealed interface ImportResult {
        data class Applied(val emojiCategory: EmojiCategory) : ImportResult
        data class Rejected(val reason: String) : ImportResult
    }

    fun exportBytes(reader: EmojiCategoryPreferenceReader): ByteArray =
        KeyboardPortablePreferenceExport.create(reader).toByteArray(Charsets.UTF_8)

    fun importBytes(
        bytes: ByteArray,
        writer: EmojiCategoryPreferenceWriter,
    ): ImportResult {
        if (bytes.size > MAX_IMPORT_BYTES) {
            return ImportResult.Rejected("portable preference file exceeds the bounded size limit")
        }

        val encoded = bytes.toString(Charsets.UTF_8)
        if (!encoded.toByteArray(Charsets.UTF_8).contentEquals(bytes)) {
            return ImportResult.Rejected("portable preference file must be canonical UTF-8")
        }

        return when (val result = KeyboardPortablePreferenceImport.apply(encoded, writer)) {
            is KeyboardPortablePreferenceImport.ApplyResult.Applied ->
                ImportResult.Applied(result.emojiCategory)
            is KeyboardPortablePreferenceImport.ApplyResult.Invalid ->
                ImportResult.Rejected(result.reason)
        }
    }
}
