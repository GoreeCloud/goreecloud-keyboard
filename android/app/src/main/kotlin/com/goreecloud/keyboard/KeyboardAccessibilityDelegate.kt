package com.goreecloud.keyboard

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import kotlin.math.roundToInt

internal data class KeyboardAccessibilityTarget(
    val id: Int,
    val bounds: android.graphics.RectF,
    val label: String,
    val selected: Boolean = false,
)

/**
 * Exposes the custom-drawn KeyboardView controls as real virtual accessibility nodes.
 *
 * This delegate deliberately consumes only the already-rendered interaction geometry and
 * visible labels supplied by KeyboardView. It does not inspect InputConnection/editor
 * contents, clipboard state, suggestion history, network data, or any hidden input state.
 */
internal class KeyboardAccessibilityDelegate(
    private val keyboardView: KeyboardView,
) : ExploreByTouchHelper(keyboardView) {

    override fun getVirtualViewAt(x: Float, y: Float): Int =
        keyboardView.accessibilityTargets()
            .lastOrNull { it.bounds.contains(x, y) }
            ?.id
            ?: INVALID_ID

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
        keyboardView.accessibilityTargets().forEach { virtualViewIds += it.id }
    }

    override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat,
    ) {
        val target = keyboardView.accessibilityTarget(virtualViewId)
        if (target == null) {
            node.contentDescription = "Unavailable keyboard control"
            node.className = "android.widget.Button"
            node.isEnabled = false
            node.setBoundsInParent(Rect(0, 0, 1, 1))
            return
        }

        node.contentDescription = target.label
        node.className = "android.widget.Button"
        node.isEnabled = true
        node.isFocusable = true
        node.isClickable = true
        node.isSelected = target.selected
        node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)
        node.setBoundsInParent(target.bounds.toAccessibilityRect())
    }

    override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?,
    ): Boolean {
        if (action != AccessibilityNodeInfo.ACTION_CLICK) return false
        if (!keyboardView.performAccessibilityTarget(virtualViewId)) return false
        sendEventForVirtualView(virtualViewId, AccessibilityEvent.TYPE_VIEW_CLICKED)
        return true
    }

    fun invalidateVirtualRoot() {
        invalidateRoot()
    }

    private fun android.graphics.RectF.toAccessibilityRect(): Rect {
        val left = left.roundToInt()
        val top = top.roundToInt()
        val right = right.roundToInt().coerceAtLeast(left + 1)
        val bottom = bottom.roundToInt().coerceAtLeast(top + 1)
        return Rect(left, top, right, bottom)
    }
}
