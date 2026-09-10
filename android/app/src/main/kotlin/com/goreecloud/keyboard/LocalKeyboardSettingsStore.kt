package com.goreecloud.keyboard

import android.content.Context

/**
 * Device-local, low-sensitivity presentation preferences for GoreeCloud Keyboard.
 *
 * This store must not contain typed text, composing context, suggestions, clipboard contents,
 * credentials, editor content, or usage-derived history. Toolbar and cursor-control preferences
 * select only already-implemented local controls; they are intentionally outside the portable
 * preference format.
 */
class LocalKeyboardSettingsStore(context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun showNumberRow(): Boolean =
        preferences.getBoolean(KEY_SHOW_NUMBER_ROW, DEFAULT_SHOW_NUMBER_ROW)

    fun setShowNumberRow(value: Boolean) {
        preferences.edit().putBoolean(KEY_SHOW_NUMBER_ROW, value).apply()
    }

    fun spacebarCursorControlEnabled(): Boolean =
        preferences.getBoolean(KEY_SPACEBAR_CURSOR_CONTROL, DEFAULT_SPACEBAR_CURSOR_CONTROL)

    fun setSpacebarCursorControlEnabled(value: Boolean) {
        preferences.edit().putBoolean(KEY_SPACEBAR_CURSOR_CONTROL, value).apply()
    }

    fun toolbarConfiguration(): KeyboardToolbarConfiguration = KeyboardToolbarConfiguration(
        enabled = preferences.getBoolean(KEY_SHOW_UTILITY_TOOLBAR, DEFAULT_SHOW_UTILITY_TOOLBAR),
        showEmoji = preferences.getBoolean(KEY_TOOLBAR_EMOJI, DEFAULT_TOOLBAR_EMOJI),
        showSymbols = preferences.getBoolean(KEY_TOOLBAR_SYMBOLS, DEFAULT_TOOLBAR_SYMBOLS),
        showSettings = preferences.getBoolean(KEY_TOOLBAR_SETTINGS, DEFAULT_TOOLBAR_SETTINGS),
    )

    fun setShowUtilityToolbar(value: Boolean) {
        preferences.edit().putBoolean(KEY_SHOW_UTILITY_TOOLBAR, value).apply()
    }

    fun setToolbarEmoji(value: Boolean) {
        preferences.edit().putBoolean(KEY_TOOLBAR_EMOJI, value).apply()
    }

    fun setToolbarSymbols(value: Boolean) {
        preferences.edit().putBoolean(KEY_TOOLBAR_SYMBOLS, value).apply()
    }

    fun setToolbarSettings(value: Boolean) {
        preferences.edit().putBoolean(KEY_TOOLBAR_SETTINGS, value).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "goreecloud_keyboard_settings"
        const val KEY_SHOW_NUMBER_ROW = "show_number_row"
        const val KEY_SPACEBAR_CURSOR_CONTROL = "spacebar_cursor_control"
        const val KEY_SHOW_UTILITY_TOOLBAR = "show_utility_toolbar"
        const val KEY_TOOLBAR_EMOJI = "toolbar_emoji"
        const val KEY_TOOLBAR_SYMBOLS = "toolbar_symbols"
        const val KEY_TOOLBAR_SETTINGS = "toolbar_settings"

        const val DEFAULT_SHOW_NUMBER_ROW = true
        const val DEFAULT_SPACEBAR_CURSOR_CONTROL = true
        const val DEFAULT_SHOW_UTILITY_TOOLBAR = true
        const val DEFAULT_TOOLBAR_EMOJI = true
        const val DEFAULT_TOOLBAR_SYMBOLS = true
        const val DEFAULT_TOOLBAR_SETTINGS = true
    }
}
