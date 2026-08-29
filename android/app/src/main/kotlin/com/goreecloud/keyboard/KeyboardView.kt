package com.goreecloud.keyboard

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
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
        fun onLayerChanged(layer: KeyboardLayer)
    }

    var listener: Listener? = null

    private data class Key(val label: String, val weight: Float = 1f, val action: Action)
    private enum class Action { CHARACTER, SHIFT, BACKSPACE, SPACE, ENTER, MODE }
    private data class HitKey(val bounds: RectF, val key: Key)
    private data class HitSuggestion(val bounds: RectF, val value: String)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val keyPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val keyStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.displayMetrics.density
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 20f * resources.displayMetrics.scaledDensity
        typeface = Typeface.create("sans", Typeface.NORMAL)
    }
    private val suggestionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 14f * resources.displayMetrics.scaledDensity
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }
    private val suggestionHintPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 13f * resources.displayMetrics.scaledDensity
    }

    private val hitKeys = mutableListOf<HitKey>()
    private val hitSuggestions = mutableListOf<HitSuggestion>()
    private var shifted = false
    private var suggestions: List<String> = emptyList()
    private var layer = KeyboardLayer.LETTERS

    fun setShifted(value: Boolean) {
        shifted = value && layer == KeyboardLayer.LETTERS
        invalidate()
    }

    fun setLayer(value: KeyboardLayer) {
        layer = value
        if (layer != KeyboardLayer.LETTERS) shifted = false
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
        applyCurrentAppearance()
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        hitKeys.clear()
        hitSuggestions.clear()

        val rows = currentRows()
        val density = resources.displayMetrics.density
        val horizontalPadding = GlazeKeyboardTokens.Space2Dp * density
        val gap = GlazeKeyboardTokens.Space1Dp * density
        val topArea = GlazeKeyboardTokens.SuggestionStripHeightDp * density
        val keyboardTop = topArea + GlazeKeyboardTokens.Space2Dp * density
        val rowHeight = max(1f, (height - keyboardTop - gap * 5) / rows.size)
        val keyRadius = GlazeKeyboardTokens.RadiusMediumDp * density

        drawSuggestionStrip(canvas, horizontalPadding, topArea)

        rows.forEachIndexed { rowIndex, row ->
            val totalWeight = row.sumOf { it.weight.toDouble() }.toFloat()
            val availableWidth = width - horizontalPadding * 2 - gap * (row.size - 1)
            var left = horizontalPadding
            val top = keyboardTop + rowIndex * (rowHeight + gap)

            row.forEach { key ->
                val keyWidth = availableWidth * (key.weight / totalWeight)
                val bounds = RectF(left, top, left + keyWidth, top + rowHeight)
                canvas.drawRoundRect(bounds, keyRadius, keyRadius, keyPaint)
                canvas.drawRoundRect(bounds, keyRadius, keyRadius, keyStrokePaint)

                val label = if (key.action == Action.CHARACTER && shifted && layer == KeyboardLayer.LETTERS) {
                    key.label.uppercase()
                } else key.label
                val baseline = bounds.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(label, bounds.centerX(), baseline, textPaint)
                hitKeys += HitKey(bounds, key)
                left += keyWidth + gap
            }
        }
    }

    private fun currentRows(): List<List<Key>> {
        val characterRows = KeyboardLayout.characterRows(layer)
        return when (layer) {
            KeyboardLayer.LETTERS -> listOf(
                characterRows[0].map(::characterKey),
                characterRows[1].map(::characterKey),
                listOf(Key("⇧", 1.25f, Action.SHIFT)) +
                    characterRows[2].map(::characterKey) +
                    listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(
                    Key("?123", 1.4f, Action.MODE),
                    Key("space", 5f, Action.SPACE),
                    Key("↵", 1.4f, Action.ENTER)
                )
            )
            KeyboardLayer.SYMBOLS -> listOf(
                characterRows[0].map(::characterKey),
                characterRows[1].map(::characterKey),
                characterRows[2].map(::characterKey) + listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(
                    Key("ABC", 1.4f, Action.MODE),
                    Key("space", 5f, Action.SPACE),
                    Key("↵", 1.4f, Action.ENTER)
                )
            )
        }
    }

    private fun characterKey(value: Char): Key = Key(value.toString(), action = Action.CHARACTER)

    private fun applyCurrentAppearance() {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val appearance = if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            GlazeKeyboardTokens.Appearance.DARK
        } else {
            GlazeKeyboardTokens.Appearance.LIGHT
        }
        val palette = GlazeKeyboardTokens.palette(appearance)
        backgroundPaint.color = palette.canvasArgb
        keyPaint.color = palette.surfaceArgb
        keyStrokePaint.color = palette.lineArgb
        textPaint.color = palette.onSurfaceArgb
        suggestionPaint.color = palette.onSurfaceArgb
        suggestionHintPaint.color = palette.onSurfaceMutedArgb
    }

    private fun drawSuggestionStrip(canvas: Canvas, horizontalPadding: Float, topArea: Float) {
        if (layer == KeyboardLayer.SYMBOLS) {
            val baseline = topArea / 2f - (suggestionHintPaint.descent() + suggestionHintPaint.ascent()) / 2
            canvas.drawText(
                "Symbols stay local",
                width / 2f,
                baseline,
                suggestionHintPaint
            )
            return
        }

        if (suggestions.isEmpty()) {
            val baseline = topArea / 2f - (suggestionHintPaint.descent() + suggestionHintPaint.ascent()) / 2
            canvas.drawText(
                "Quill suggestions stay on-device",
                width / 2f,
                baseline,
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
            Action.MODE -> {
                layer = if (layer == KeyboardLayer.LETTERS) KeyboardLayer.SYMBOLS else KeyboardLayer.LETTERS
                shifted = false
                listener?.onLayerChanged(layer)
                invalidate()
            }
        }
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
