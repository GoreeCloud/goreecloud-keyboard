package com.goreecloud.keyboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeyboardInputSurfaceRuntimeTest {
    @Test
    fun dedicatedNumberRowCanBeEnabledAndDisabledOnTheRealNativeSurface() {
        val view = createView()
        view.setNumberRowEnabled(true)
        render(view)

        val enabledLabels = view.accessibilityTargets().map { it.label }
        for (digit in "1234567890") {
            assertTrue("enabled number row must expose $digit", enabledLabels.contains(digit.toString()))
        }

        view.setNumberRowEnabled(false)
        render(view)
        val disabledLabels = view.accessibilityTargets().map { it.label }
        for (digit in "1234567890") {
            assertFalse("disabled number row must remove $digit from letters layer", disabledLabels.contains(digit.toString()))
        }
    }

    @Test
    fun adaptiveEditorActionChangesTheRenderedAccessibilityContract() {
        val view = createView()
        view.setEditorAction(EditorActionPolicy.resolve(EditorInfo.IME_ACTION_DONE))
        render(view)
        assertTrue(view.accessibilityTargets().any { it.label == "Done" })

        view.setEditorAction(EditorActionPolicy.resolve(EditorInfo.IME_ACTION_SEARCH))
        render(view)
        assertTrue(view.accessibilityTargets().any { it.label == "Search" })
        assertFalse(view.accessibilityTargets().any { it.label == "Done" })
    }

    @Test
    fun configurableUtilityToolbarExposesOnlyEnabledRealActions() {
        val view = createView()
        view.setToolbarConfiguration(
            KeyboardToolbarConfiguration(
                enabled = true,
                showEmoji = false,
                showSymbols = false,
                showSettings = true,
            )
        )
        render(view)

        val toolbarTargets = view.accessibilityTargets().filter { it.id >= 5_000 }
        assertEquals(listOf("Keyboard settings"), toolbarTargets.map { it.label })

        var settingsRequests = 0
        view.listener = listener(onOpenSettings = { settingsRequests += 1 })
        assertTrue(view.performAccessibilityTarget(toolbarTargets.single().id))
        assertEquals("Toolbar Settings must route through the real listener action", 1, settingsRequests)

        view.setToolbarConfiguration(
            KeyboardToolbarConfiguration(
                enabled = true,
                showEmoji = false,
                showSymbols = false,
                showSettings = false,
            )
        )
        render(view)
        assertTrue(
            "A toolbar with no enabled real actions must collapse instead of rendering placeholders",
            view.accessibilityTargets().none { it.id >= 5_000 },
        )
    }

    @Test
    fun utilityToolbarLayerActionsReuseExistingLayerRouting() {
        val view = createView()
        val layers = mutableListOf<KeyboardLayer>()
        view.listener = listener(onLayerChanged = { layers += it })
        view.setToolbarConfiguration(
            KeyboardToolbarConfiguration(
                enabled = true,
                showEmoji = false,
                showSymbols = true,
                showSettings = false,
            )
        )
        render(view)

        val symbols = view.accessibilityTargets().first { it.id >= 5_000 && it.label == "Symbols" }
        assertTrue(view.performAccessibilityTarget(symbols.id))
        assertEquals(listOf(KeyboardLayer.SYMBOLS), layers)

        render(view)
        val selectedSymbols = view.accessibilityTargets().first { it.id >= 5_000 && it.label == "Symbols" }
        assertTrue("Toolbar Symbols must expose selected state on a symbol layer", selectedSymbols.selected)

        view.setToolbarConfiguration(
            KeyboardToolbarConfiguration(
                enabled = true,
                showEmoji = true,
                showSymbols = false,
                showSettings = false,
            )
        )
        render(view)
        val emoji = view.accessibilityTargets().first { it.id >= 5_000 && it.label == "Emoji" }
        assertTrue(view.performAccessibilityTarget(emoji.id))
        assertEquals(listOf(KeyboardLayer.SYMBOLS, KeyboardLayer.EMOJI), layers)

        render(view)
        val selectedEmoji = view.accessibilityTargets().first { it.id >= 5_000 && it.label == "Emoji" }
        assertTrue("Toolbar Emoji must expose selected state on the emoji layer", selectedEmoji.selected)
    }

    @Test
    fun numberRowPreferenceRemainsDeviceLocalAndExplicit() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val store = LocalKeyboardSettingsStore(context)

        store.setShowNumberRow(false)
        assertFalse(store.showNumberRow())
        store.setShowNumberRow(true)
        assertTrue(store.showNumberRow())
    }

    @Test
    fun utilityToolbarPreferencesRemainDeviceLocalAndExplicit() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val store = LocalKeyboardSettingsStore(context)

        store.setShowUtilityToolbar(false)
        store.setToolbarEmoji(false)
        store.setToolbarSymbols(true)
        store.setToolbarSettings(false)
        var configuration = store.toolbarConfiguration()
        assertFalse(configuration.enabled)
        assertFalse(configuration.showEmoji)
        assertTrue(configuration.showSymbols)
        assertFalse(configuration.showSettings)

        store.setShowUtilityToolbar(true)
        store.setToolbarEmoji(true)
        store.setToolbarSymbols(true)
        store.setToolbarSettings(true)
        configuration = store.toolbarConfiguration()
        assertTrue(configuration.enabled)
        assertEquals(
            listOf(
                KeyboardToolbarAction.EMOJI,
                KeyboardToolbarAction.SYMBOLS,
                KeyboardToolbarAction.SETTINGS,
            ),
            configuration.visibleActions(),
        )
    }

    private fun createView(): KeyboardView {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        return KeyboardView(context).apply {
            measure(
                View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(720, View.MeasureSpec.EXACTLY),
            )
            layout(0, 0, measuredWidth, measuredHeight)
        }
    }

    private fun render(view: KeyboardView) {
        view.draw(
            Canvas(
                Bitmap.createBitmap(
                    view.measuredWidth,
                    view.measuredHeight,
                    Bitmap.Config.ARGB_8888,
                ),
            ),
        )
    }

    private fun listener(
        onLayerChanged: (KeyboardLayer) -> Unit = {},
        onOpenSettings: () -> Unit = {},
    ) = object : KeyboardView.Listener {
        override fun onText(value: String) = Unit
        override fun onSpace() = Unit
        override fun onBackspace() = Unit
        override fun onEnter() = Unit
        override fun onShift() = Unit
        override fun onSuggestion(value: String) = Unit
        override fun onLayerChanged(layer: KeyboardLayer) = onLayerChanged(layer)
        override fun onOpenSettings() = onOpenSettings()
    }
}
