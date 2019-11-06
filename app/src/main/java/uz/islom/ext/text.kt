package uz.islom.ext

import android.content.Context
import android.graphics.*
import android.text.*
import androidx.core.content.res.ResourcesCompat
import uz.islom.R
import uz.islom.ui.custom.TrimmedTextView
import kotlin.math.roundToInt


fun String.ellipsize(): SpannableString {
    val s = SpannableString(this)
    s.setSpan(TrimmedTextView.EllipsizeRange.ELLIPSIS_AT_END, 0, s.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return s
}

fun Context.drawTextToBitmap(text: String, width: Int, height: Int, textColor: Int): Bitmap {

    val scale = resources.displayMetrics.density

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.color = textColor
    paint.typeface = ResourcesCompat.getFont(this, R.font.p604)
    paint.textSize = (16 * scale).roundToInt().toFloat()

    val values = DrawingValues(text, dp(32), Layout.Alignment.ALIGN_NORMAL, paint, true, Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888))

    return values.draw(values.defaultPosition)
}


data class DrawingValues(
        val text: String,
        val padding: Int,
        val alignment: Layout.Alignment,
        val paint: Paint,
        val isMultiline: Boolean,
        val bitmap: Bitmap
) {

    val defaultPosition: DrawingValues.() -> Unit = {
        val canvas = Canvas(bitmap)
        val textWidth = canvas.width - padding
        val textLayout = StaticLayout(text, TextPaint(paint), textWidth, alignment, 1f, 0f, false)

        val bounds = Rect().apply {
            if (isMultiline) {
                top = 0
                left = 0
                right = textWidth
                bottom = textLayout.height
            } else {
                paint.getTextBounds(text, 0, text.length, this)
            }
        }

        val x = (bitmap.width - bounds.width()) / 2f
        val y = (bitmap.height - bounds.height()) / 2f

        if (!isMultiline) {
            canvas.drawText(text, x, y, paint)
        } else {
            canvas.save()
            canvas.translate(x, y)
            textLayout.draw(canvas)
            canvas.restore()
        }
    }

    fun draw(adjustmentBody: DrawingValues.() -> Unit): Bitmap {
        this.adjustmentBody()
        return bitmap
    }

}