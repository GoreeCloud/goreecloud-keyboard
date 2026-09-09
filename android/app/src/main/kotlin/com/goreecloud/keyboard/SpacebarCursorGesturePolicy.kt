package com.goreecloud.keyboard

import kotlin.math.abs
import kotlin.math.floor

internal enum class SpacebarCursorGestureMode {
    PENDING,
    CURSOR,
    CANCEL,
}

internal data class SpacebarCursorGestureDecision(
    val mode: SpacebarCursorGestureMode,
    val cumulativeSteps: Int = 0,
)

/**
 * Pure gesture policy for spacebar cursor control.
 *
 * The policy observes movement only; it has no editor, text, clipboard, persistence, network, or
 * telemetry authority. Horizontal movement must dominate before cursor mode activates. Once the
 * activation distance is crossed, the first cursor step is emitted and additional steps are spaced
 * by [stepDistancePx]. A vertical-dominant gesture past activation fails closed instead of turning
 * into another key action.
 */
internal object SpacebarCursorGesturePolicy {
    fun evaluate(
        deltaX: Float,
        deltaY: Float,
        activationDistancePx: Float,
        stepDistancePx: Float,
    ): SpacebarCursorGestureDecision {
        if (!activationDistancePx.isFinite() || activationDistancePx <= 0f ||
            !stepDistancePx.isFinite() || stepDistancePx <= 0f ||
            !deltaX.isFinite() || !deltaY.isFinite()
        ) {
            return SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CANCEL)
        }

        val horizontal = abs(deltaX)
        val vertical = abs(deltaY)
        if (horizontal < activationDistancePx && vertical < activationDistancePx) {
            return SpacebarCursorGestureDecision(SpacebarCursorGestureMode.PENDING)
        }
        if (horizontal <= vertical) {
            return SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CANCEL)
        }

        val additionalSteps = floor((horizontal - activationDistancePx) / stepDistancePx)
            .toInt()
            .coerceAtLeast(0)
        val magnitude = 1 + additionalSteps
        val direction = if (deltaX < 0f) -1 else 1
        return SpacebarCursorGestureDecision(
            mode = SpacebarCursorGestureMode.CURSOR,
            cumulativeSteps = direction * magnitude,
        )
    }
}
