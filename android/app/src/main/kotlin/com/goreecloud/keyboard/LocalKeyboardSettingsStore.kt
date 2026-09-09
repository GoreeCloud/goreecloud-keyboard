package com.goreecloud.keyboard

import android.content.Context

/**
 * Device-local, low-sensitivity presentation preferences for GoreeCloud Keyboard.
 *
 * This store must not contain typed text, composing context, suggestions, clipboard contents,
 * credentials, editor content, or usage-derived history.
 */
class LocalKeyboardSettingsStore(context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun showNumberRow(): Boolean =
        preferences.getBoolean(KEY_SHOW_NUMBER_ROW, DEFAULT_SHOW_NUMBER_ROW)

    fun setShowNumberRow(value: Boolean) {
        preferences.edit().putBoolean(KEY_SHOW_NUMBER_ROW, value).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "goreecloud_keyboard_settings"
        const val KEY_SHOW_NUMBER_ROW = "show_number_row"
        const val DEFAULT_SHOW_NUMBER_ROW = true
    }
}
