package com.goreecloud.keyboard

import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

/**
 * Observes only touches that begin on the already-rendered Space accessibility target.
 *
 * Ordinary taps continue through KeyboardView unchanged. Once a horizontal cursor gesture is
 * established, the listener cancels the normal key press and owns the remainder of that gesture so
 * releasing the finger cannot also commit a space or another key. No editor text is inspected.
 */
internal class SpacebarCursorTouchListener(
    private val keyboardView: KeyboardView,
    private val isEnabled: () -> Boolean,
    private val onCursorSteps: (Int) -> Unit,
    private val activationDistancePx: Float =
        ViewConfiguration.get(keyboardView.context).scaledTouchSlop.toFloat(),
    private val stepDistancePx: Float = 24f * keyboardView.resources.displayMetrics.density,
) : View.OnTouchListener {
    private var spaceBounds: RectF? = null
    private var startX = 0f
    private var startY = 0f
    private var emittedSteps = 0
    private var cursorMode = false
    private var consumeUntilUp = false

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                reset()
                if (!isEnabled()) return false
                val target = keyboardView.accessibilityTargets()
                    .firstOrNull { it.label == SPACE_ACCESSIBILITY_LABEL && it.bounds.contains(event.x, event.y) }
                    ?: return false
                spaceBounds = RectF(target.bounds)
                startX = event.x
                startY = event.y
                return false
            }

            MotionEvent.ACTION_MOVE -> {
                if (spaceBounds == null) return consumeUntilUp
                val decision = if (cursorMode) {
                    SpacebarCursorGesturePolicy.evaluate(
                        deltaX = event.x - startX,
                        deltaY = 0f,
                        activationDistancePx = activationDistancePx,
                        stepDistancePx = stepDistancePx,
                    )
                } else {
                    SpacebarCursorGesturePolicy.evaluate(
                        deltaX = event.x - startX,
                        deltaY = event.y - startY,
                        activationDistancePx = activationDistancePx,
                        stepDistancePx = stepDistancePx,
                    )
                }

                return when (decision.mode) {
                    SpacebarCursorGestureMode.PENDING -> false
                    SpacebarCursorGestureMode.CANCEL -> {
                        cancelNormalKeyboardTouch(event)
                        spaceBounds = null
                        consumeUntilUp = true
                        true
                    }
                    SpacebarCursorGestureMode.CURSOR -> {
                        if (!cursorMode) {
                            cursorMode = true
                            consumeUntilUp = true
                            cancelNormalKeyboardTouch(event)
                        }
                        val stepDelta = decision.cumulativeSteps - emittedSteps
                        if (stepDelta != 0) {
                            emittedSteps = decision.cumulativeSteps
                            onCursorSteps(stepDelta)
                        }
                        true
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                val consume = cursorMode || consumeUntilUp
                reset()
                return consume
            }

            MotionEvent.ACTION_CANCEL -> {
                reset()
                return false
            }
        }
        return cursorMode || consumeUntilUp
    }

    private fun cancelNormalKeyboardTouch(source: MotionEvent) {
        val cancel = MotionEvent.obtain(source)
        cancel.action = MotionEvent.ACTION_CANCEL
        keyboardView.onTouchEvent(cancel)
        cancel.recycle()
    }

    private fun reset() {
        spaceBounds = null
        startX = 0f
        startY = 0f
        emittedSteps = 0
        cursorMode = false
        consumeUntilUp = false
    }

    private companion object {
        const val SPACE_ACCESSIBILITY_LABEL = "Space"
    }
}
