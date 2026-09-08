package com.goreecloud.keyboard

import android.view.inputmethod.EditorInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EditorActionPolicyTest {
    @Test
    fun explicitEditorActionsMapToCompactLabelsAndPlatformActionIds() {
        val cases = listOf(
            Triple(EditorInfo.IME_ACTION_GO, "Go", "Go"),
            Triple(EditorInfo.IME_ACTION_SEARCH, "⌕", "Search"),
            Triple(EditorInfo.IME_ACTION_SEND, "Send", "Send"),
            Triple(EditorInfo.IME_ACTION_NEXT, "Next", "Next"),
            Triple(EditorInfo.IME_ACTION_DONE, "Done", "Done"),
            Triple(EditorInfo.IME_ACTION_PREVIOUS, "Prev", "Previous"),
        )

        for ((actionId, visibleLabel, accessibilityLabel) in cases) {
            val presentation = EditorActionPolicy.resolve(actionId)
            assertEquals(visibleLabel, presentation.visibleLabel)
            assertEquals(accessibilityLabel, presentation.accessibilityLabel)
            assertEquals(actionId, presentation.actionId)
        }
    }

    @Test
    fun unspecifiedAndNoEnterActionOptionsFallBackToPlainEnter() {
        for (imeOptions in listOf(
            EditorInfo.IME_ACTION_UNSPECIFIED,
            EditorInfo.IME_ACTION_NONE,
            EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_ENTER_ACTION,
        )) {
            val presentation = EditorActionPolicy.resolve(imeOptions)
            assertEquals("↵", presentation.visibleLabel)
            assertEquals("Enter", presentation.accessibilityLabel)
            assertNull(presentation.actionId)
        }
    }
}
