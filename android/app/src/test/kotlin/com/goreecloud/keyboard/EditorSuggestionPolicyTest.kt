package com.goreecloud.keyboard

import android.text.InputType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EditorSuggestionPolicyTest {
    @Test
    fun suppressesEveryExistingSensitivePasswordClass() {
        assertTrue(
            EditorSuggestionPolicy.shouldSuppress(
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
            ),
        )
        assertTrue(
            EditorSuggestionPolicy.shouldSuppress(
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD,
            ),
        )
    }

    @Test
    fun honorsTextEditorNoSuggestionsFlag() {
        val inputType = InputType.TYPE_CLASS_TEXT or
            InputType.TYPE_TEXT_VARIATION_NORMAL or
            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        assertTrue(EditorSuggestionPolicy.shouldSuppress(inputType))
        assertFalse(InputPrivacyClassifier.isSensitive(inputType))
    }

    @Test
    fun ordinaryTextAndEmailEditorsKeepLocalSuggestionsEligible() {
        assertFalse(
            EditorSuggestionPolicy.shouldSuppress(
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL,
            ),
        )
        assertFalse(
            EditorSuggestionPolicy.shouldSuppress(
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
            ),
        )
    }

    @Test
    fun textOnlyFlagDoesNotReclassifyNonTextEditors() {
        val numericWithTextFlag = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        assertFalse(EditorSuggestionPolicy.shouldSuppress(numericWithTextFlag))
    }
}
