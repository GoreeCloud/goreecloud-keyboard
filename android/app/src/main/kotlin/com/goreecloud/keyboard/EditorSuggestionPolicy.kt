package com.goreecloud.keyboard

import android.text.InputType

/**
 * Determines whether GoreeCloud Keyboard may collect transient composing-word context or display
 * local suggestion candidates for the current editor.
 *
 * Password/sensitive fields remain governed by [InputPrivacyClassifier]. Ordinary text editors may
 * also explicitly request no suggestions through Android's TYPE_TEXT_FLAG_NO_SUGGESTIONS flag. That
 * host request suppresses suggestions without reclassifying the editor as a password field or
 * expanding the stricter sensitive-editor surrounding-text policy.
 */
object EditorSuggestionPolicy {
    fun shouldSuppress(inputType: Int): Boolean {
        if (InputPrivacyClassifier.isSensitive(inputType)) return true

        val inputClass = inputType and InputType.TYPE_MASK_CLASS
        val flags = inputType and InputType.TYPE_MASK_FLAGS
        return inputClass == InputType.TYPE_CLASS_TEXT &&
            flags and InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS != 0
    }
}
