package com.goreecloud.keyboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
        assertTrue("Rendered q virtual target must have positive width", q.bounds.width() > 0f)
        assertTrue("Rendered q virtual target must have positive height", q.bounds.height() > 0f)

        // Exercise Android's actual platform provider bridge. ViewCompat's wrapper around an
        // already-created platform provider intentionally does not proxy its compat methods.
        val provider = view.accessibilityNodeProvider
        assertNotNull("ExploreByTouchHelper must expose a platform node provider", provider)
        val node = provider!!.createAccessibilityNodeInfo(q.id)
        assertNotNull("Virtual q node must be creatable from its exposed target ID", node)
        assertEquals("Virtual q node must expose its rendered label", "q", node!!.contentDescription?.toString())
        assertEquals(
            "Virtual q node must expose button semantics",
            "android.widget.Button",
            node.className?.toString(),
        )
        assertTrue("Virtual q node must expose clickable semantics", node.isClickable)

        val committed = mutableListOf<String>()
        view.listener = listener(onText = { committed += it })
        assertTrue(
            "Accessibility ACTION_CLICK must be handled by the virtual q node",
            provider.performAction(q.id, AccessibilityNodeInfo.ACTION_CLICK, null),
        )
        assertEquals(
            "Accessibility activation must use the normal listener path",
            listOf("q"),
            committed,
        )
    }

    @Test
    fun longPressAlternatesAreDiscoverableAndActionableThroughNativeNodeActions() {
        val view = createRenderedKeyboard()
        val a = view.accessibilityTargets().first { it.label == "a" }
        val provider = view.accessibilityNodeProvider
        assertNotNull("ExploreByTouchHelper must expose a platform node provider", provider)

        val node = provider!!.createAccessibilityNodeInfo(a.id)
        assertNotNull("Virtual a node must be creatable", node)
        assertTrue("Keys with local alternates must expose long-click semantics", node!!.isLongClickable)
        assertTrue(
            "Keys with local alternates must expose ACTION_LONG_CLICK",
            node.actionList.any { it.id == AccessibilityNodeInfo.ACTION_LONG_CLICK },
        )
        assertEquals(
            "Alternate discovery hint must come from the localized resource boundary",
            view.context.getString(R.string.accessibility_alternates_available),
            node.hintText?.toString(),
        )

        val expectedInsertAcute = view.context.getString(
            R.string.accessibility_insert_alternate,
            "á",
        )
        val insertAcute = node.actionList.firstOrNull {
            it.label?.toString() == expectedInsertAcute
        }
        assertNotNull("Local acute-a alternate must be discoverable as a custom action", insertAcute)

        val committed = mutableListOf<String>()
        view.listener = listener(onText = { committed += it })
        assertTrue(
            "Long click must provide an accessibility discovery path for alternates",
            provider.performAction(a.id, AccessibilityNodeInfo.ACTION_LONG_CLICK, null),
        )
        assertTrue(
            "Alternate custom action must be handled by the virtual key",
            provider.performAction(a.id, insertAcute!!.id, null),
        )
        assertEquals(
            "Alternate accessibility activation must use the normal text listener path",
            listOf("á"),
            committed,
        )
    }

    @Test
    fun suggestionsAndEmojiControlsRemainDiscoverableAndActionable() {
        val view = createRenderedKeyboard(listOf("hello", "help", "hero"))
        val selectedSuggestions = mutableListOf<String>()
        view.listener = listener(onSuggestion = { selectedSuggestions += it })

        var targets = view.accessibilityTargets()
        val hello = targets.first { it.label == "Suggestion hello" }
        assertTrue("Suggestion virtual target must activate", view.performAccessibilityTarget(hello.id))
        assertEquals("Suggestion activation must use the normal suggestion listener", listOf("hello"), selectedSuggestions)

        view.setLayer(KeyboardLayer.EMOJI)
        render(view)
        targets = view.accessibilityTargets()
        val search = targets.first { it.label == "Search emoji" }
        assertTrue("Search emoji virtual control must activate", view.performAccessibilityTarget(search.id))

        render(view)
        targets = view.accessibilityTargets()
        assertTrue("Emoji search must expose Clear", targets.any { it.label == "Clear emoji search" })
        assertTrue("Emoji search must expose Close", targets.any { it.label == "Close emoji search" })
        val q = targets.first { it.label == "q" }
        val provider = view.accessibilityNodeProvider
        assertNotNull("Emoji search must retain the platform accessibility provider", provider)
        val qNode = provider!!.createAccessibilityNodeInfo(q.id)
        assertNotNull("Emoji-search q node must be creatable", qNode)
        assertFalse(
            "Emoji-search query keys must not gain long-press alternate semantics",
            qNode!!.isLongClickable,
        )
        val insertPrefix = view.context.getString(
            R.string.accessibility_insert_alternate,
            "",
        ).trim()
        assertFalse(
            "Emoji-search query keys must not expose alternate custom actions",
            qNode.actionList.any { it.label?.toString()?.startsWith(insertPrefix) == true },
        )
    }

    @Test
    fun utilityToolbarSettingsActionIsAResourceBackedNativeVirtualControl() {
        val view = createRenderedKeyboard()
        view.setToolbarConfiguration(
            KeyboardToolbarConfiguration(
                enabled = true,
                showEmoji = false,
                showSymbols = false,
                showSettings = true,
            )
        )
        render(view)

        val target = view.accessibilityTargets().first { it.id >= 5_000 }
        assertEquals(
            view.context.getString(R.string.accessibility_toolbar_settings),
            target.label,
        )
        val provider = view.accessibilityNodeProvider
        assertNotNull("Toolbar must remain available through the platform accessibility provider", provider)
        val node = provider!!.createAccessibilityNodeInfo(target.id)
        assertNotNull("Toolbar Settings node must be creatable", node)
        assertEquals(target.label, node!!.contentDescription?.toString())
        assertTrue(node.isClickable)

        var settingsRequests = 0
        view.listener = listener(onOpenSettings = { settingsRequests += 1 })
        assertTrue(provider.performAction(target.id, AccessibilityNodeInfo.ACTION_CLICK, null))
        assertEquals(
            "Toolbar accessibility activation must reuse the normal Settings listener path",
            1,
            settingsRequests,
        )
    }

    @Test
    fun selectedVirtualStateTracksShiftEmojiCategoryAndToolbarPresentation() {
        val view = createRenderedKeyboard()
        var targets = view.accessibilityTargets()
        var shift = targets.first { it.label == "Shift" }
        var provider = view.accessibilityNodeProvider
        assertNotNull("ExploreByTouchHelper must expose a platform node provider", provider)
        var shiftNode = provider!!.createAccessibilityNodeInfo(shift.id)
        assertNotNull("Shift virtual node must be creatable", shiftNode)
        assertEquals(
            "Unshifted state must be available without relying on selection visuals",
            view.context.getString(R.string.accessibility_state_off),
            shiftNode!!.stateDescription?.toString(),
        )

        view.setShifted(true)
        render(view)

        targets = view.accessibilityTargets()
        shift = targets.first { it.label == "Shift" }
        assertTrue("Shift virtual node must expose selected state", shift.selected)
        assertTrue("Shifted text keys must expose rendered uppercase labels", targets.any { it.label == "Q" })
        provider = view.accessibilityNodeProvider
        shiftNode = provider!!.createAccessibilityNodeInfo(shift.id)
        assertEquals(
            "Shifted state must be explicitly described",
            view.context.getString(R.string.accessibility_state_on),
            shiftNode!!.stateDescription?.toString(),
        )

        view.setLayer(KeyboardLayer.EMOJI)
        render(view)
        targets = view.accessibilityTargets()
        val selectedCategories = targets.filter { it.selected && it.label.endsWith(" emoji") }
        assertEquals(
            "Exactly one ordinary emoji category should expose selected state initially",
            1,
            selectedCategories.size,
        )
        val selectedCategoryNode = view.accessibilityNodeProvider!!
            .createAccessibilityNodeInfo(selectedCategories.single().id)
        assertEquals(
            "Selected emoji category must expose a textual state description",
            view.context.getString(R.string.accessibility_state_selected),
            selectedCategoryNode!!.stateDescription?.toString(),
        )

        val selectedToolbarEmoji = targets.first { it.id >= 5_000 && it.label == "Emoji" }
        assertTrue("Toolbar Emoji must expose selected state on the emoji layer", selectedToolbarEmoji.selected)
        val selectedToolbarNode = view.accessibilityNodeProvider!!
            .createAccessibilityNodeInfo(selectedToolbarEmoji.id)
        assertEquals(
            "Selected toolbar state must be available without relying on visual treatment",
            view.context.getString(R.string.accessibility_state_selected),
            selectedToolbarNode!!.stateDescription?.toString(),
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
        onOpenSettings: () -> Unit = {},
    ) = object : KeyboardView.Listener {
        override fun onText(value: String) = onText(value)
        override fun onSpace() = Unit
        override fun onBackspace() = Unit
        override fun onEnter() = Unit
        override fun onShift() = Unit
        override fun onSuggestion(value: String) = onSuggestion(value)
        override fun onLayerChanged(layer: KeyboardLayer) = Unit
        override fun onOpenSettings() = onOpenSettings()
    }
}
