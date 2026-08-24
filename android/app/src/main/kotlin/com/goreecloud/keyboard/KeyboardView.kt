package com.goreecloud.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    interface Listener {
        fun onCharacter(value: Char)
        fun onSpace()
        fun onBackspace()
        fun onEnter()
        fun onShift()
        fun onSuggestion(value: String)
    }

    var listener: Listener? = null

    private data class Key(val label: String, val weight: Float = 1f, val action: Action)
    private enum class Action { CHARACTER, SHIFT, BACKSPACE, SPACE, ENTER }
    private data class HitKey(val bounds: RectF, val key: Key)
    private data class HitSuggestion(val bounds: RectF, val value: String)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFFF6F8FC.toInt() }
    private val keyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFFFFFFFF.toInt() }
    private val keyStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x1F0F172A
        style = Paint.Style.STROKE
        strokeWidth = resources.displayMetrics.density
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF172033.toInt()
        textAlign = Paint.Align.CENTER
        textSize = 20f * resources.displayMetrics.scaledDensity
        typeface = android.graphics.Typeface.create("sans", android.graphics.Typeface.NORMAL)
    }
    private val suggestionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF334155.toInt()
        textAlign = Paint.Align.CENTER
        textSize = 14f * resources.displayMetrics.scaledDensity
        typeface = android.graphics.Typeface.create("sans", android.graphics.Typeface.MEDIUM)
    }
    private val suggestionHintPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF64748B.toInt()
        textAlign = Paint.Align.CENTER
        textSize = 13f * resources.displayMetrics.scaledDensity
    }

    private val rows = listOf(
        "qwertyuiop".map { Key(it.toString(), action = Action.CHARACTER) },
        "asdfghjkl".map { Key(it.toString(), action = Action.CHARACTER) },
        listOf(Key("⇧", 1.25f, Action.SHIFT)) +
            "zxcvbnm".map { Key(it.toString(), action = Action.CHARACTER) } +
            listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
        listOf(
            Key("space", 5f, Action.SPACE),
            Key("↵", 1.4f, Action.ENTER)
        )
    )

    private val hitKeys = mutableListOf<HitKey>()
    private val hitSuggestions = mutableListOf<HitSuggestion>()
    private var shifted = false
    private var suggestions: List<String> = emptyList()

    fun setShifted(value: Boolean) {
        shifted = value
        invalidate()
    }

    fun setSuggestions(values: List<String>) {
        suggestions = values.take(3)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val preferredHeight = (300f * resources.displayMetrics.density).toInt()
        val height = resolveSize(preferredHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        hitKeys.clear()
        hitSuggestions.clear()

        val density = resources.displayMetrics.density
        val horizontalPadding = 6f * density
        val gap = 5f * density
        val topArea = 42f * density
        val keyboardTop = topArea + 4f * density
        val rowHeight = max(1f, (height - keyboardTop - gap * 5) / rows.size)

        drawSuggestionStrip(canvas, horizontalPadding, topArea, density)

        rows.forEachIndexed { rowIndex, row ->
            val totalWeight = row.sumOf { it.weight.toDouble() }.toFloat()
            val availableWidth = width - horizontalPadding * 2 - gap * (row.size - 1)
            var left = horizontalPadding
            val top = keyboardTop + rowIndex * (rowHeight + gap)

            row.forEach { key ->
                val keyWidth = availableWidth * (key.weight / totalWeight)
                val bounds = RectF(left, top, left + keyWidth, top + rowHeight)
                canvas.drawRoundRect(bounds, 12f * density, 12f * density, keyPaint)
                canvas.drawRoundRect(bounds, 12f * density, 12f * density, keyStrokePaint)

                val label = if (key.action == Action.CHARACTER && shifted) {
                    key.label.uppercase()
                } else key.label
                val baseline = bounds.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(label, bounds.centerX(), baseline, textPaint)
                hitKeys += HitKey(bounds, key)
                left += keyWidth + gap
            }
        }
    }

    private fun drawSuggestionStrip(canvas: Canvas, horizontalPadding: Float, topArea: Float, density: Float) {
        if (suggestions.isEmpty()) {
            canvas.drawText(
                "Quill suggestions stay on-device",
                width / 2f,
                26f * density,
                suggestionHintPaint
            )
            return
        }

        val cellWidth = (width - horizontalPadding * 2) / suggestions.size
        suggestions.forEachIndexed { index, suggestion ->
            val bounds = RectF(
                horizontalPadding + cellWidth * index,
                0f,
                horizontalPadding + cellWidth * (index + 1),
                topArea
            )
            val baseline = bounds.centerY() - (suggestionPaint.descent() + suggestionPaint.ascent()) / 2
            canvas.drawText(suggestion, bounds.centerX(), baseline, suggestionPaint)
            hitSuggestions += HitSuggestion(bounds, suggestion)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_UP) return true

        hitSuggestions.lastOrNull { it.bounds.contains(event.x, event.y) }?.let { hit ->
            listener?.onSuggestion(hit.value)
            performClick()
            return true
        }

        val hit = hitKeys.lastOrNull { it.bounds.contains(event.x, event.y) } ?: return true
        when (hit.key.action) {
            Action.CHARACTER -> listener?.onCharacter(hit.key.label[0])
            Action.SHIFT -> listener?.onShift()
            Action.BACKSPACE -> listener?.onBackspace()
            Action.SPACE -> listener?.onSpace()
            Action.ENTER -> listener?.onEnter()
        }
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
