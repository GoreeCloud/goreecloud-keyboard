package com.goreecloud.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardService : InputMethodService(), KeyboardView.Listener {
    private var shifted = false
    private var keyboardView: KeyboardView? = null

    override fun onCreateInputView(): View {
        return KeyboardView(this).also { view ->
            keyboardView = view
            view.listener = this
            view.setShifted(shifted)
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        shifted = false
        keyboardView?.setShifted(false)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        shifted = false
        keyboardView?.setShifted(false)
    }

    override fun onDestroy() {
        keyboardView = null
        super.onDestroy()
    }

    override fun onCharacter(value: Char) {
        val output = if (shifted) value.uppercaseChar() else value
        currentInputConnection?.commitText(output.toString(), 1)
        if (shifted) {
            shifted = false
            keyboardView?.setShifted(false)
        }
    }

    override fun onSpace() {
        currentInputConnection?.commitText(" ", 1)
    }

    override fun onBackspace() {
        currentInputConnection?.deleteSurroundingText(1, 0)
    }

    override fun onEnter() {
        val connection = currentInputConnection ?: return
        connection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        connection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
    }

    override fun onShift() {
        shifted = !shifted
        keyboardView?.setShifted(shifted)
    }
}
