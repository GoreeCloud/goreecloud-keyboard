package com.goreecloud.keyboard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Explicit user-controlled file transfer surface for the one-field portable Keyboard preference.
 *
 * The activity uses Android's Storage Access Framework so the user chooses every source or
 * destination document. Import selection validates and previews the one category before any local
 * preference write. It requests no broad storage or network permission and does not expose typed
 * text, emoji recents, clipboard state, search history, learned input, telemetry, Identity data,
 * or credentials.
 */
class KeyboardPortablePreferencesActivity : Activity() {
    private lateinit var categoryStore: LocalEmojiCategoryStore
    private lateinit var categoryView: TextView
    private lateinit var statusView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.portable_preferences_title)
        categoryStore = LocalEmojiCategoryStore(this)

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val padding = dp(24)
            setPadding(padding, padding, padding, padding)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }

        content.addView(TextView(this).apply {
            text = getString(R.string.portable_preferences_summary)
            textSize = 18f
        }, matchWidth())

        categoryView = TextView(this).apply {
            textSize = 16f
            setPadding(0, dp(20), 0, dp(12))
        }
        content.addView(categoryView, matchWidth())

        content.addView(Button(this).apply {
            text = getString(R.string.portable_preferences_export)
            minHeight = dp(GlazeKeyboardTokens.GeneralInteractionFloorDp.toInt())
            setOnClickListener { launchExport() }
        }, matchWidth())

        content.addView(Button(this).apply {
            text = getString(R.string.portable_preferences_import)
            minHeight = dp(GlazeKeyboardTokens.GeneralInteractionFloorDp.toInt())
            setOnClickListener { launchImport() }
        }, matchWidth())

        content.addView(TextView(this).apply {
            text = getString(R.string.portable_preferences_privacy_note)
            setPadding(0, dp(16), 0, dp(8))
        }, matchWidth())

        statusView = TextView(this).apply {
            accessibilityLiveRegion = TextView.ACCESSIBILITY_LIVE_REGION_POLITE
        }
        content.addView(statusView, matchWidth())

        setContentView(content)
        refreshCategory()
    }

    @Deprecated("Uses the platform result callback to preserve the current minimal Activity dependency surface")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        val uri = data?.data ?: return

        when (requestCode) {
            REQUEST_EXPORT -> exportTo(uri)
            REQUEST_IMPORT -> previewImportFrom(uri)
        }
    }

    private fun launchExport() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = KeyboardPortablePreferenceTransfer.MIME_TYPE
            putExtra(Intent.EXTRA_TITLE, KeyboardPortablePreferenceTransfer.EXPORT_FILE_NAME)
        }
        @Suppress("DEPRECATION")
        startActivityForResult(intent, REQUEST_EXPORT)
    }

    private fun launchImport() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = KeyboardPortablePreferenceTransfer.MIME_TYPE
        }
        @Suppress("DEPRECATION")
        startActivityForResult(intent, REQUEST_IMPORT)
    }

    private fun exportTo(uri: Uri) {
        try {
            val bytes = KeyboardPortablePreferenceTransfer.exportBytes(categoryStore)
            contentResolver.openOutputStream(uri, "wt")?.use { output ->
                output.write(bytes)
                output.flush()
            } ?: throw IOException("destination could not be opened")
            statusView.text = getString(R.string.portable_preferences_exported)
        } catch (_: IOException) {
            statusView.text = getString(R.string.portable_preferences_export_failed)
        } catch (_: SecurityException) {
            statusView.text = getString(R.string.portable_preferences_export_failed)
        }
    }

    private fun previewImportFrom(uri: Uri) {
        try {
            val bytes = readBounded(uri)
            when (val result = KeyboardPortablePreferenceTransfer.previewImportBytes(bytes)) {
                is KeyboardPortablePreferenceTransfer.PreviewResult.Ready ->
                    showImportPreview(result.preview)

                is KeyboardPortablePreferenceTransfer.PreviewResult.Rejected -> {
                    statusView.text = getString(
                        R.string.portable_preferences_import_rejected,
                        result.reason,
                    )
                }
            }
        } catch (_: SizeLimitExceeded) {
            statusView.text = getString(R.string.portable_preferences_import_too_large)
        } catch (_: IOException) {
            statusView.text = getString(R.string.portable_preferences_import_failed)
        } catch (_: SecurityException) {
            statusView.text = getString(R.string.portable_preferences_import_failed)
        }
    }

    private fun showImportPreview(preview: KeyboardPortablePreferenceTransfer.ImportPreview) {
        val category = displayCategory(preview.emojiCategory)
        AlertDialog.Builder(this)
            .setTitle(R.string.portable_preferences_import_preview_title)
            .setMessage(getString(R.string.portable_preferences_import_preview_message, category))
            .setNegativeButton(R.string.portable_preferences_import_cancel) { _, _ ->
                statusView.text = getString(R.string.portable_preferences_import_cancelled)
            }
            .setPositiveButton(R.string.portable_preferences_import_apply) { _, _ ->
                KeyboardPortablePreferenceTransfer.applyPreview(preview, categoryStore)
                refreshCategory()
                statusView.text = getString(R.string.portable_preferences_imported)
            }
            .show()
    }

    private fun readBounded(uri: Uri): ByteArray {
        val input = contentResolver.openInputStream(uri)
            ?: throw IOException("source could not be opened")
        input.use { stream ->
            val output = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var total = 0
            while (true) {
                val read = stream.read(buffer)
                if (read < 0) break
                total += read
                if (total > KeyboardPortablePreferenceTransfer.MAX_IMPORT_BYTES) {
                    throw SizeLimitExceeded()
                }
                output.write(buffer, 0, read)
            }
            return output.toByteArray()
        }
    }

    private fun refreshCategory() {
        categoryView.text = getString(
            R.string.portable_preferences_current_category,
            displayCategory(categoryStore.loadEmojiCategory()),
        )
    }

    private fun displayCategory(category: EmojiCategory): String =
        category.name
            .lowercase()
            .replace('_', ' ')
            .replaceFirstChar { it.titlecase() }

    private fun matchWidth(): LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
    ).apply {
        bottomMargin = dp(8)
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private class SizeLimitExceeded : IOException()

    private companion object {
        const val REQUEST_EXPORT = 501
        const val REQUEST_IMPORT = 502
    }
}
