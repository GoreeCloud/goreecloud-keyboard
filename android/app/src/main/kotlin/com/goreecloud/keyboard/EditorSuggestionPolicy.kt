package com.goreecloud.keyboard

import android.text.InputType
import android.view.inputmethod.EditorInfo

/**
 * Determines whether GoreeCloud Keyboard may collect transient composing-word context or display
 * local suggestion candidates for the current editor.
 *
 * Password/sensitive fields remain governed by [InputPrivacyClassifier]. Ordinary text editors may
 * explicitly request no suggestions through Android's TYPE_TEXT_FLAG_NO_SUGGESTIONS flag. Editors
 * may also request IME_FLAG_NO_PERSONALIZED_LEARNING. GoreeCloud Keyboard does not currently build
 * a learned user model, but it treats that flag as a stronger privacy boundary and suppresses the
 * transient suggestion context as well rather than retaining editor text the host asked the IME not
 * to use for personalization.
 */
object EditorSuggestionPolicy {
    fun shouldSuppress(inputType: Int, imeOptions: Int = 0): Boolean {
        if (InputPrivacyClassifier.isSensitive(inputType)) return true
        if (imeOptions and EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING != 0) return true

        val inputClass = inputType and InputType.TYPE_MASK_CLASS
        val flags = inputType and InputType.TYPE_MASK_FLAGS
        return inputClass == InputType.TYPE_CLASS_TEXT &&
            flags and InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS != 0
    }
}
