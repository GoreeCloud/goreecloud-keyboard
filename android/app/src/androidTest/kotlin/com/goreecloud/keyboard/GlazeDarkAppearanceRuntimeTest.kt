package com.goreecloud.keyboard

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GlazeDarkAppearanceRuntimeTest {
    @Test
    fun keyboardRendersCanonicalLightAndDarkCanvasOnDevice() {
        val base = ApplicationProvider.getApplicationContext<Context>()

        val light = render(base, Configuration.UI_MODE_NIGHT_NO)
        val dark = render(base, Configuration.UI_MODE_NIGHT_YES)

        assertEquals(GlazeKeyboardTokens.LightPalette.canvasArgb, light.getPixel(0, 0))
        assertEquals(GlazeKeyboardTokens.DarkPalette.canvasArgb, dark.getPixel(0, 0))
        assertTrue(light.getPixel(0, 0) != dark.getPixel(0, 0))
    }

    @Test
    fun renderedKeyboardKeepsGeneralInteractionFloorInSuggestionArea() {
        val base = ApplicationProvider.getApplicationContext<Context>()
        val context = contextForNightMode(base, Configuration.UI_MODE_NIGHT_YES)
        val view = KeyboardView(context)
        val density = context.resources.displayMetrics.density

        view.measure(
            View.MeasureSpec.makeMeasureSpec((360 * density).toInt(), View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec((300 * density).toInt(), View.MeasureSpec.EXACTLY),
        )

        assertTrue(view.measuredHeight >= (GlazeKeyboardTokens.SuggestionStripHeightDp * density).toInt())
        assertEquals(48f, GlazeKeyboardTokens.GeneralInteractionFloorDp)
    }

    private fun render(base: Context, nightMode: Int): Bitmap {
        val context = contextForNightMode(base, nightMode)
        val density = context.resources.displayMetrics.density
        val width = (360 * density).toInt()
        val height = (300 * density).toInt()
        val view = KeyboardView(context)
        view.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY),
        )
        view.layout(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        return bitmap
    }

    private fun contextForNightMode(base: Context, nightMode: Int): Context {
        val configuration = Configuration(base.resources.configuration)
        configuration.uiMode =
            (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or nightMode
        return base.createConfigurationContext(configuration)
    }
}
