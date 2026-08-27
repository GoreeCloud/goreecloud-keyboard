package com.goreecloud.keyboard

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

private object GlazeMotionExperimental {
    const val VERSION = "0.5.0"
    const val REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
    const val RUNTIME_BASELINE = "0.4.0"
    const val MICRO_DURATION_MS = 90L
    const val REDUCED_MOTION_DURATION_MS = 0L
    const val PRESS_SCALE = 0.98f
    const val MAXIMUM_CONCURRENT_SETTLING = 12

    fun durationMs(reducedMotion: Boolean): Long =
        if (reducedMotion) REDUCED_MOTION_DURATION_MS else MICRO_DURATION_MS

    fun allowsOptionalSettling(reducedMotion: Boolean, activeSettling: Int): Boolean =
        !reducedMotion && activeSettling < MAXIMUM_CONCURRENT_SETTLING
}

@RunWith(AndroidJUnit4::class)
class GlazeMotionExperimentalKeyboardRuntimeTest {
    @Test
    fun disabledPlatformAnimationsCollapseOptionalMotionWithoutBlockingKeyCommit() {
        assertFalse(ValueAnimator.areAnimatorsEnabled())
        assertEquals(0L, GlazeMotionExperimental.durationMs(reducedMotion = true))
        assertFalse(
            GlazeMotionExperimental.allowsOptionalSettling(
                reducedMotion = true,
                activeSettling = 0
            )
        )

        val view = createRenderedKeyboard()
        val events = mutableListOf<String>()
        view.listener = listener(onCharacter = { events += it.toString() })

        val density = view.resources.displayMetrics.density
        val x = 6f * density + ((view.width - 12f * density - 45f * density) / 10f) / 2f
        val keyboardTop = 46f * density
        val rowHeight = (view.height - keyboardTop - 25f * density) / 4f
        val y = keyboardTop + rowHeight / 2f

        dispatch(view, MotionEvent.ACTION_DOWN, x, y)
        assertTrue(events.isEmpty())
        dispatch(view, MotionEvent.ACTION_UP, x, y)
        assertEquals(listOf("q"), events)
    }

    @Test
    fun realSuggestionHitTestingRemainsSemanticStateSource() {
        val view = createRenderedKeyboard(listOf("hello", "help", "hero"))
        val selected = mutableListOf<String>()
        view.listener = listener(onSuggestion = { selected += it })

        val density = view.resources.displayMetrics.density
        val horizontalPadding = 6f * density
        val cellWidth = (view.width - horizontalPadding * 2f) / 3f
        dispatch(view, MotionEvent.ACTION_UP, horizontalPadding + cellWidth / 2f, 21f * density)

        assertEquals(listOf("hello"), selected)
        assertEquals("0.5.0", GlazeMotionExperimental.VERSION)
        assertEquals("0.4.0", GlazeMotionExperimental.RUNTIME_BASELINE)
        assertEquals(90L, GlazeMotionExperimental.durationMs(reducedMotion = false))
        assertTrue(GlazeMotionExperimental.PRESS_SCALE < 1f)
    }

    private fun createRenderedKeyboard(suggestions: List<String> = emptyList()): KeyboardView {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        return KeyboardView(context).apply {
            setSuggestions(suggestions)
            measure(
                View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
            )
            layout(0, 0, measuredWidth, measuredHeight)
            draw(Canvas(Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)))
        }
    }

    private fun dispatch(view: KeyboardView, action: Int, x: Float, y: Float) {
        val event = MotionEvent.obtain(0L, 0L, action, x, y, 0)
        try {
            view.dispatchTouchEvent(event)
        } finally {
            event.recycle()
        }
    }

    private fun listener(
        onCharacter: (Char) -> Unit = {},
        onSuggestion: (String) -> Unit = {}
    ) = object : KeyboardView.Listener {
        override fun onCharacter(value: Char) = onCharacter(value)
        override fun onSpace() = Unit
        override fun onBackspace() = Unit
        override fun onEnter() = Unit
        override fun onShift() = Unit
        override fun onSuggestion(value: String) = onSuggestion(value)
    }
}
