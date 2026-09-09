package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Test

class SpacebarCursorGesturePolicyTest {
    @Test
    fun movementInsideActivationWindowStaysPending() {
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.PENDING),
            SpacebarCursorGesturePolicy.evaluate(
                deltaX = 7f,
                deltaY = 2f,
                activationDistancePx = 8f,
                stepDistancePx = 20f,
            ),
        )
    }

    @Test
    fun horizontalDragEmitsDirectionalCumulativeSteps() {
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CURSOR, cumulativeSteps = 1),
            SpacebarCursorGesturePolicy.evaluate(8f, 0f, 8f, 20f),
        )
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CURSOR, cumulativeSteps = 2),
            SpacebarCursorGesturePolicy.evaluate(29f, 1f, 8f, 20f),
        )
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CURSOR, cumulativeSteps = -3),
            SpacebarCursorGesturePolicy.evaluate(-49f, 2f, 8f, 20f),
        )
    }

    @Test
    fun extremeFiniteHorizontalSamplesRemainBoundedAndDirectional() {
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CURSOR, cumulativeSteps = 4096),
            SpacebarCursorGesturePolicy.evaluate(Float.MAX_VALUE, 0f, 8f, 1f),
        )
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CURSOR, cumulativeSteps = -4096),
            SpacebarCursorGesturePolicy.evaluate(-Float.MAX_VALUE, 0f, 8f, 1f),
        )
    }

    @Test
    fun verticalDominantGestureFailsClosed() {
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CANCEL),
            SpacebarCursorGesturePolicy.evaluate(
                deltaX = 9f,
                deltaY = 12f,
                activationDistancePx = 8f,
                stepDistancePx = 20f,
            ),
        )
    }

    @Test
    fun equalAxisMovementDoesNotBecomeCursorControl() {
        assertEquals(
            SpacebarCursorGestureDecision(SpacebarCursorGestureMode.CANCEL),
            SpacebarCursorGesturePolicy.evaluate(10f, 10f, 8f, 20f),
        )
    }

    @Test
    fun invalidGeometryFailsClosed() {
        assertEquals(
            SpacebarCursorGestureMode.CANCEL,
            SpacebarCursorGesturePolicy.evaluate(10f, 0f, 0f, 20f).mode,
        )
        assertEquals(
            SpacebarCursorGestureMode.CANCEL,
            SpacebarCursorGesturePolicy.evaluate(Float.NaN, 0f, 8f, 20f).mode,
        )
    }
}
