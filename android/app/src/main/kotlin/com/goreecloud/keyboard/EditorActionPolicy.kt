package com.goreecloud.keyboard

import android.view.inputmethod.EditorInfo

data class EditorActionPresentation(
    val visibleLabel: String,
    val accessibilityLabel: String,
    val actionId: Int?,
)

object EditorActionPolicy {
    val Enter = EditorActionPresentation(
        visibleLabel = "↵",
        accessibilityLabel = "Enter",
        actionId = null,
    )

    fun resolve(imeOptions: Int): EditorActionPresentation {
        if (imeOptions and EditorInfo.IME_FLAG_NO_ENTER_ACTION != 0) {
            return Enter
        }

        return when (imeOptions and EditorInfo.IME_MASK_ACTION) {
            EditorInfo.IME_ACTION_GO -> EditorActionPresentation("Go", "Go", EditorInfo.IME_ACTION_GO)
            EditorInfo.IME_ACTION_SEARCH -> EditorActionPresentation("⌕", "Search", EditorInfo.IME_ACTION_SEARCH)
            EditorInfo.IME_ACTION_SEND -> EditorActionPresentation("Send", "Send", EditorInfo.IME_ACTION_SEND)
            EditorInfo.IME_ACTION_NEXT -> EditorActionPresentation("Next", "Next", EditorInfo.IME_ACTION_NEXT)
            EditorInfo.IME_ACTION_DONE -> EditorActionPresentation("Done", "Done", EditorInfo.IME_ACTION_DONE)
            EditorInfo.IME_ACTION_PREVIOUS -> EditorActionPresentation("Prev", "Previous", EditorInfo.IME_ACTION_PREVIOUS)
            else -> Enter
        }
    }
}
