package com.goreecloud.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardService : InputMethodService(), KeyboardView.Listener {
    private var shifted = false
    private var sensitiveInput = false
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
            view.setLayer(KeyboardLayer.LETTERS)
            view.setShifted(shifted)
            updateSuggestions()
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        shifted = false
        sensitiveInput = InputPrivacyClassifier.isSensitive(info?.inputType ?: 0)
        composingWord.clear()
        keyboardView?.setLayer(KeyboardLayer.LETTERS)
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        shifted = false
        sensitiveInput = false
        composingWord.clear()
        keyboardView?.setLayer(KeyboardLayer.LETTERS)
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onDestroy() {
        composingWord.clear()
        keyboardView = null
        super.onDestroy()
    }

    override fun onText(value: String) {
        if (value.isEmpty()) return
        val isLetterText = value.codePoints().allMatch { Character.isLetter(it) }
        val output = if (shifted && isLetterText) value.uppercase() else value
        currentInputConnection?.commitText(output, 1)
        if (!sensitiveInput) {
            if (isLetterText) {
                composingWord.append(output.lowercase())
            } else {
                composingWord.clear()
            }
        }
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
        connection.deleteSurroundingTextInCodePoints(1, 0)
        if (!sensitiveInput && composingWord.isNotEmpty()) {
            val lastCodePointStart = composingWord.offsetByCodePoints(composingWord.length, -1)
            composingWord.delete(lastCodePointStart, composingWord.length)
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
        if (sensitiveInput) return
        val connection = currentInputConnection ?: return
        val prefixCodePoints = composingWord.codePointCount(0, composingWord.length)
        if (prefixCodePoints > 0) {
            connection.deleteSurroundingTextInCodePoints(prefixCodePoints, 0)
        }
        connection.commitText("$value ", 1)
        composingWord.clear()
        shifted = false
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onLayerChanged(layer: KeyboardLayer) {
        shifted = false
        composingWord.clear()
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    private fun updateSuggestions() {
        if (sensitiveInput) {
            keyboardView?.setSuggestions(emptyList())
            return
        }
        val suggestions = suggestionEngine.suggest(
            prefix = composingWord.toString(),
            dictionary = bootstrapDictionary
        )
        keyboardView?.setSuggestions(suggestions)
    }
}
