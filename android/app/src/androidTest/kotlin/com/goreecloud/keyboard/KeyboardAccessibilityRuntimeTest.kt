package com.goreecloud.keyboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.ViewCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeyboardAccessibilityRuntimeTest {
    @Test
    fun renderedKeysAreVirtualButtonsAndAccessibilityClickUsesRealTextPath() {
        val view = createRenderedKeyboard()
        val targets = view.accessibilityTargets()
        assertTrue("Rendered keyboard must expose virtual controls", targets.isNotEmpty())
        assertEquals("Virtual target IDs must be unique", targets.size, targets.map { it.id }.toSet().size)

        val q = targets.first { it.label == "q" }
        assertTrue(q.bounds.width() > 0f)
        assertTrue(q.bounds.height() > 0f)

        val provider = ViewCompat.getAccessibilityNodeProvider(view)
        assertNotNull("ExploreByTouchHelper must expose a node provider", provider)
        val node = provider!!.createAccessibilityNodeInfo(q.id)
        assertNotNull(node)
        assertEquals("q", node!!.contentDescription)
        assertEquals("android.widget.Button", node.className)
        assertTrue(node.isClickable)

        val committed = mutableListOf<String>()
        view.listener = listener(onText = { committed += it })
        assertTrue(provider.performAction(q.id, AccessibilityNodeInfo.ACTION_CLICK, null))
        assertEquals("Accessibility activation must use the normal listener path", listOf("q"), committed)
    }

    @Test
    fun suggestionsAndEmojiControlsRemainDiscoverableAndActionable() {
        val view = createRenderedKeyboard(listOf("hello", "help", "hero"))
        val selectedSuggestions = mutableListOf<String>()
        view.listener = listener(onSuggestion = { selectedSuggestions += it })

        var targets = view.accessibilityTargets()
        val hello = targets.first { it.label == "Suggestion hello" }
        assertTrue(view.performAccessibilityTarget(hello.id))
        assertEquals(listOf("hello"), selectedSuggestions)

        view.setLayer(KeyboardLayer.EMOJI)
        render(view)
        targets = view.accessibilityTargets()
        val search = targets.first { it.label == "Search emoji" }
        assertTrue(view.performAccessibilityTarget(search.id))

        render(view)
        targets = view.accessibilityTargets()
        assertTrue(targets.any { it.label == "Clear emoji search" })
        assertTrue(targets.any { it.label == "Close emoji search" })
        assertTrue(targets.any { it.label == "q" })
    }

    @Test
    fun selectedVirtualStateTracksShiftAndEmojiCategoryPresentation() {
        val view = createRenderedKeyboard()
        view.setShifted(true)
        render(view)

        var targets = view.accessibilityTargets()
        val shift = targets.first { it.label == "Shift" }
        assertTrue("Shift virtual node must expose selected state", shift.selected)
        assertTrue(targets.any { it.label == "Q" })

        view.setLayer(KeyboardLayer.EMOJI)
        render(view)
        targets = view.accessibilityTargets()
        assertTrue(
            "Exactly one ordinary emoji category should expose selected state initially",
            targets.count { it.selected && it.label.endsWith(" emoji") } == 1,
        )
    }

    private fun createRenderedKeyboard(suggestions: List<String> = emptyList()): KeyboardView {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        return KeyboardView(context).apply {
            setSuggestions(suggestions)
            measure(
                View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY),
            )
            layout(0, 0, measuredWidth, measuredHeight)
            render(this)
        }
    }

    private fun render(view: KeyboardView) {
        view.draw(Canvas(Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)))
    }

    private fun listener(
        onText: (String) -> Unit = {},
        onSuggestion: (String) -> Unit = {},
    ) = object : KeyboardView.Listener {
        override fun onText(value: String) = onText(value)
        override fun onSpace() = Unit
        override fun onBackspace() = Unit
        override fun onEnter() = Unit
        override fun onShift() = Unit
        override fun onSuggestion(value: String) = onSuggestion(value)
        override fun onLayerChanged(layer: KeyboardLayer) = Unit
    }
}
