package com.goreecloud.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardService : InputMethodService(), KeyboardView.Listener {
    private var shifted = false

    override fun onCreateInputView(): View = KeyboardView(this).also {
        it.listener = this
        it.setShifted(shifted)
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        shifted = false
        (inputView as? KeyboardView)?.setShifted(false)
    }

    override fun onCharacter(value: Char) {
        val output = if (shifted) value.uppercaseChar() else value
        currentInputConnection?.commitText(output.toString(), 1)
        if (shifted) {
            shifted = false
            (inputView as? KeyboardView)?.setShifted(false)
        }
    }

    override fun onSpace() {
        currentInputConnection?.commitText(" ", 1)
    }

    override fun onBackspace() {
        currentInputConnection?.deleteSurroundingText(1, 0)
    }

    override fun onEnter() {
        currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
    }

    override fun onShift() {
        shifted = !shifted
        (inputView as? KeyboardView)?.setShifted(shifted)
    }
}
