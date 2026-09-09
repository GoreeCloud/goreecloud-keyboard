package com.goreecloud.keyboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun numberRowPreferenceRemainsDeviceLocalAndExplicit() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val store = LocalKeyboardSettingsStore(context)

        store.setShowNumberRow(false)
        assertFalse(store.showNumberRow())
        store.setShowNumberRow(true)
        assertTrue(store.showNumberRow())
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
}
