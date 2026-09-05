package com.goreecloud.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardService : InputMethodService(), KeyboardView.Listener {
    private var shifted = false
    private var sensitiveInput = false
    private var suggestionsSuppressed = false
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

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        // Editor authority changes before the input view is necessarily shown. Reset the privacy
        // policy and transient composing state here so an editor switch cannot temporarily retain
        // the previous field's sensitive/no-suggestions decision while the IME UI is hidden.
        beginEditorSession(attribute)
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        // Android can show/restart the input view after onStartInput. Re-evaluate from the current
        // EditorInfo rather than trusting cached policy from a previous visible field.
        beginEditorSession(info)
        keyboardView?.setLayer(KeyboardLayer.LETTERS)
        keyboardView?.setShifted(false)
        updateSuggestions()
    }

    override fun onFinishInput() {
        super.onFinishInput()
        // onFinishInput is Android's editor-session boundary. Do not rely only on the input view
        // being hidden to clear transient composing context or the previous editor's policy state.
        resetEditorSession()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        // The view can finish independently of the editor session. Clear the same transient state
        // here as a defense-in-depth UI boundary; onStartInputView will reapply current policy.
        resetEditorSession()
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
        if (!suggestionsSuppressed) {
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
        val deleteCodePoints = if (sensitiveInput) {
            1
        } else {
            val beforeCursor = connection.getTextBeforeCursor(BACKSPACE_LOOKBEHIND_UTF16, 0)
            TextDeletion.previousTextUnitCodePointCount(beforeCursor ?: "").takeIf { it > 0 } ?: 1
        }
        connection.deleteSurroundingTextInCodePoints(deleteCodePoints, 0)

        if (!suggestionsSuppressed && composingWord.isNotEmpty()) {
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
        if (suggestionsSuppressed) return
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

    private fun beginEditorSession(info: EditorInfo?) {
        shifted = false
        val inputType = info?.inputType ?: 0
        sensitiveInput = InputPrivacyClassifier.isSensitive(inputType)
        suggestionsSuppressed = EditorSuggestionPolicy.shouldSuppress(inputType)
        composingWord.clear()
    }

    private fun resetEditorSession() {
        shifted = false
        sensitiveInput = false
        suggestionsSuppressed = false
        composingWord.clear()
        keyboardView?.setLayer(KeyboardLayer.LETTERS)
        keyboardView?.setShifted(false)
        // No active editor owns suggestion presentation after teardown. Clear the visible strip
        // rather than repopulating bootstrap candidates until a subsequent editor session starts.
        keyboardView?.setSuggestions(emptyList())
    }

    private fun updateSuggestions() {
        if (suggestionsSuppressed) {
            keyboardView?.setSuggestions(emptyList())
            return
        }
        val suggestions = suggestionEngine.suggest(
            prefix = composingWord.toString(),
            dictionary = bootstrapDictionary
        )
        keyboardView?.setSuggestions(suggestions)
    }

    private companion object {
        const val BACKSPACE_LOOKBEHIND_UTF16 = 64
    }
}
