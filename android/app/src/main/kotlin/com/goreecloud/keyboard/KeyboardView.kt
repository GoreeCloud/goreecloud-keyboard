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
        fun onText(value: String)
        fun onSpace()
        fun onBackspace()
        fun onEnter()
        fun onShift()
        fun onSuggestion(value: String)
        fun onLayerChanged(layer: KeyboardLayer)
    }

    var listener: Listener? = null

    private data class Key(val label: String, val weight: Float = 1f, val action: Action)
    private enum class Action {
        TEXT, SHIFT, BACKSPACE, SPACE, ENTER, LETTERS, SYMBOLS, SYMBOLS_MORE, EMOJI,
    }
    private data class HitKey(val bounds: RectF, val key: Key)
    private data class HitSuggestion(val bounds: RectF, val value: String)
    private data class HitEmojiCategory(val bounds: RectF, val entry: EmojiStripEntry)

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
    private val hitEmojiCategories = mutableListOf<HitEmojiCategory>()
    private val emojiRecentsStore = LocalEmojiRecentsStore(context)
    private val emojiRecents = EmojiRecents(initialValues = emojiRecentsStore.load())
    private var shifted = false
    private var suggestions: List<String> = emptyList()
    private var layer = KeyboardLayer.LETTERS
    private var emojiCategory = EmojiCategory.SMILEYS
    private var showingEmojiRecents = false

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
        hitEmojiCategories.clear()

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

                val label = if (key.action == Action.TEXT && shifted && layer == KeyboardLayer.LETTERS) key.label.uppercase() else key.label
                val baseline = bounds.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(label, bounds.centerX(), baseline, textPaint)
                hitKeys += HitKey(bounds, key)
                left += keyWidth + gap
            }
        }
    }

    private fun currentRows(): List<List<Key>> {
        val characterRows = if (layer == KeyboardLayer.EMOJI) {
            if (showingEmojiRecents && emojiRecents.values().isNotEmpty()) emojiRecents.rows() else KeyboardLayout.emojiRows(emojiCategory)
        } else {
            KeyboardLayout.characterRows(layer)
        }
        return when (layer) {
            KeyboardLayer.LETTERS -> listOf(
                characterRows[0].map(::textKey),
                characterRows[1].map(::textKey),
                listOf(Key("⇧", 1.25f, Action.SHIFT)) + characterRows[2].map(::textKey) + listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(Key("?123", 1.3f, Action.SYMBOLS), Key("☺", 1.05f, Action.EMOJI), Key("space", 4.65f, Action.SPACE), Key("↵", 1.3f, Action.ENTER)),
            )
            KeyboardLayer.SYMBOLS -> listOf(
                characterRows[0].map(::textKey),
                characterRows[1].map(::textKey),
                characterRows[2].map(::textKey) + listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(Key("ABC", 1.15f, Action.LETTERS), Key("=\\<", 1.15f, Action.SYMBOLS_MORE), Key("☺", 1.05f, Action.EMOJI), Key("space", 3.9f, Action.SPACE), Key("↵", 1.25f, Action.ENTER)),
            )
            KeyboardLayer.SYMBOLS_MORE -> listOf(
                characterRows[0].map(::textKey),
                characterRows[1].map(::textKey),
                characterRows[2].map(::textKey) + listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(Key("ABC", 1.15f, Action.LETTERS), Key("?123", 1.15f, Action.SYMBOLS), Key("☺", 1.05f, Action.EMOJI), Key("space", 3.9f, Action.SPACE), Key("↵", 1.25f, Action.ENTER)),
            )
            KeyboardLayer.EMOJI -> listOf(
                characterRows[0].map(::textKey),
                characterRows[1].map(::textKey),
                characterRows[2].map(::textKey) + listOf(Key("⌫", 1.25f, Action.BACKSPACE)),
                listOf(Key("ABC", 1.1f, Action.LETTERS), Key("?123", 1.1f, Action.SYMBOLS), Key("space", 4.2f, Action.SPACE), Key("↵", 1.25f, Action.ENTER)),
            )
        }
    }

    private fun textKey(value: String): Key = Key(value, action = Action.TEXT)

    private fun applyCurrentAppearance() {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val appearance = if (nightMode == Configuration.UI_MODE_NIGHT_YES) GlazeKeyboardTokens.Appearance.DARK else GlazeKeyboardTokens.Appearance.LIGHT
        val palette = GlazeKeyboardTokens.palette(appearance)
        backgroundPaint.color = palette.canvasArgb
        keyPaint.color = palette.surfaceArgb
        keyStrokePaint.color = palette.lineArgb
        textPaint.color = palette.onSurfaceArgb
        suggestionPaint.color = palette.onSurfaceArgb
        suggestionHintPaint.color = palette.onSurfaceMutedArgb
    }

    private fun drawSuggestionStrip(canvas: Canvas, horizontalPadding: Float, topArea: Float) {
        if (layer == KeyboardLayer.EMOJI) {
            drawEmojiCategoryStrip(canvas, horizontalPadding, topArea)
            return
        }
        if (layer != KeyboardLayer.LETTERS) {
            val baseline = topArea / 2f - (suggestionHintPaint.descent() + suggestionHintPaint.ascent()) / 2
            canvas.drawText("Symbols stay local", width / 2f, baseline, suggestionHintPaint)
            return
        }

        if (suggestions.isEmpty()) {
            val baseline = topArea / 2f - (suggestionHintPaint.descent() + suggestionHintPaint.ascent()) / 2
            canvas.drawText("Quill suggestions stay on-device", width / 2f, baseline, suggestionHintPaint)
            return
        }

        val cellWidth = (width - horizontalPadding * 2) / suggestions.size
        suggestions.forEachIndexed { index, suggestion ->
            val bounds = RectF(horizontalPadding + cellWidth * index, 0f, horizontalPadding + cellWidth * (index + 1), topArea)
            val baseline = bounds.centerY() - (suggestionPaint.descent() + suggestionPaint.ascent()) / 2
            canvas.drawText(suggestion, bounds.centerX(), baseline, suggestionPaint)
            hitSuggestions += HitSuggestion(bounds, suggestion)
        }
    }

    private fun drawEmojiCategoryStrip(canvas: Canvas, horizontalPadding: Float, topArea: Float) {
        val entries = EmojiStripModel.entries(hasRecents = emojiRecents.values().isNotEmpty())
        val gap = GlazeKeyboardTokens.Space1Dp * resources.displayMetrics.density
        val availableWidth = width - horizontalPadding * 2 - gap * (entries.size - 1)
        val cellWidth = availableWidth / entries.size
        val radius = GlazeKeyboardTokens.RadiusMediumDp * resources.displayMetrics.density
        entries.forEachIndexed { index, entry ->
            val left = horizontalPadding + index * (cellWidth + gap)
            val bounds = RectF(left, 0f, left + cellWidth, topArea)
            val selected = if (entry.recent) showingEmojiRecents else !showingEmojiRecents && entry.category == emojiCategory
            if (selected) {
                canvas.drawRoundRect(bounds, radius, radius, keyPaint)
                canvas.drawRoundRect(bounds, radius, radius, keyStrokePaint)
            }
            val baseline = bounds.centerY() - (suggestionPaint.descent() + suggestionPaint.ascent()) / 2
            canvas.drawText(entry.visibleLabel, bounds.centerX(), baseline, suggestionPaint)
            hitEmojiCategories += HitEmojiCategory(bounds, entry)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_UP) return true

        hitEmojiCategories.lastOrNull { it.bounds.contains(event.x, event.y) }?.let { hit ->
            when {
                hit.entry.clearRecents -> {
                    emojiRecents.clear()
                    emojiRecentsStore.save(emojiRecents.values())
                    showingEmojiRecents = false
                }
                hit.entry.recent -> showingEmojiRecents = emojiRecents.values().isNotEmpty()
                hit.entry.category != null -> {
                    emojiCategory = hit.entry.category
                    showingEmojiRecents = false
                }
            }
            announceForAccessibility(hit.entry.accessibilityLabel)
            invalidate()
            performClick()
            return true
        }

        hitSuggestions.lastOrNull { it.bounds.contains(event.x, event.y) }?.let { hit ->
            listener?.onSuggestion(hit.value)
            performClick()
            return true
        }

        val hit = hitKeys.lastOrNull { it.bounds.contains(event.x, event.y) } ?: return true
        when (hit.key.action) {
            Action.TEXT -> {
                if (layer == KeyboardLayer.EMOJI) {
                    emojiRecents.record(hit.key.label)
                    emojiRecentsStore.save(emojiRecents.values())
                }
                listener?.onText(hit.key.label)
                if (layer == KeyboardLayer.EMOJI) invalidate()
            }
            Action.SHIFT -> listener?.onShift()
            Action.BACKSPACE -> listener?.onBackspace()
            Action.SPACE -> listener?.onSpace()
            Action.ENTER -> listener?.onEnter()
            Action.LETTERS -> switchLayer(KeyboardLayer.LETTERS)
            Action.SYMBOLS -> switchLayer(KeyboardLayer.SYMBOLS)
            Action.SYMBOLS_MORE -> switchLayer(KeyboardLayer.SYMBOLS_MORE)
            Action.EMOJI -> switchLayer(KeyboardLayer.EMOJI)
        }
        performClick()
        return true
    }

    private fun switchLayer(value: KeyboardLayer) {
        layer = value
        shifted = false
        listener?.onLayerChanged(layer)
        invalidate()
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
