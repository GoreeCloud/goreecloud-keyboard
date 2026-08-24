package com.goreecloud.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardService : InputMethodService(), KeyboardView.Listener {
    private var shifted = false
    private var keyboardView: KeyboardView? = null
    private val suggestionEngine = SuggestionEngine()
    private val composingWord = StringBuilder()

    private val bootstrapDictionary = listOf(
        "about", "after", "again", "because", "before", "cloud", "could", "family",
        "goreecloud", "hello", "keyboard", "message", "native", "privacy", "secure",
        "security", "suggestion", "thanks", "there", "their", "these", "typing", "where",
        "which", "would", "write", "writing"
    )

    override fun onCreateInputView(): View {
        return KeyboardView(this).also { view ->
            keyboardView = view
            view.listener = this
            view.setShifted(shifted)
            updateSuggestions()
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        shifted = false
        composingWord.clear()
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        shifted = false
        composingWord.clear()
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onDestroy() {
        keyboardView = null
        super.onDestroy()
    }

    override fun onCharacter(value: Char) {
        val output = if (shifted) value.uppercaseChar() else value
        currentInputConnection?.commitText(output.toString(), 1)
        composingWord.append(output.lowercaseChar())
        updateSuggestions()

        if (shifted) {
            shifted = false
            keyboardView?.setShifted(false)
        }
    }

    override fun onSpace() {
        currentInputConnection?.commitText(" ", 1)
        composingWord.clear()
        updateSuggestions()
    }

    override fun onBackspace() {
        val connection = currentInputConnection ?: return
        connection.deleteSurroundingText(1, 0)
        if (composingWord.isNotEmpty()) {
            composingWord.deleteCharAt(composingWord.lastIndex)
        }
        updateSuggestions()
    }

    override fun onEnter() {
        val connection = currentInputConnection ?: return
        connection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        connection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
        composingWord.clear()
        updateSuggestions()
    }

    override fun onShift() {
        shifted = !shifted
        keyboardView?.setShifted(shifted)
    }

    override fun onSuggestion(value: String) {
        val connection = currentInputConnection ?: return
        val prefixLength = composingWord.length
        if (prefixLength > 0) {
            connection.deleteSurroundingText(prefixLength, 0)
        }
        connection.commitText("$value ", 1)
        composingWord.clear()
        shifted = false
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    private fun updateSuggestions() {
        val suggestions = suggestionEngine.suggest(
            prefix = composingWord.toString(),
            dictionary = bootstrapDictionary
        )
        keyboardView?.setSuggestions(suggestions)
    }
}
