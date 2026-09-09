package com.goreecloud.keyboard

/**
 * Device-local presentation model for the bounded Keyboard utility toolbar.
 *
 * Every exposed action is backed by already-implemented local behavior. This model deliberately
 * carries no editor content, clipboard data, usage history, account state, network authority, or
 * portable/synchronized preference state.
 */
enum class KeyboardToolbarAction {
    EMOJI,
    SYMBOLS,
    SETTINGS,
}

data class KeyboardToolbarConfiguration(
    val enabled: Boolean = true,
    val showEmoji: Boolean = true,
    val showSymbols: Boolean = true,
    val showSettings: Boolean = true,
) {
    fun visibleActions(): List<KeyboardToolbarAction> {
        if (!enabled) return emptyList()
        return buildList {
            if (showEmoji) add(KeyboardToolbarAction.EMOJI)
            if (showSymbols) add(KeyboardToolbarAction.SYMBOLS)
            if (showSettings) add(KeyboardToolbarAction.SETTINGS)
        }
    }

    fun isVisible(): Boolean = visibleActions().isNotEmpty()
}
