package uz.islom.ext

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils

class Label(val text: CharSequence? = null, val ellipsize: Boolean = false) : UiComponent() {
    val paint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
            .apply {
                typeface = DEFAULT_FONT
            }

    var textColor: Int
        get() = paint.color
        set(value) {
            paint.color = value
        }

    var textSize: Float
        get() = paint.textSize
        set(value) {
            paint.textSize = value
        }

    var typeface: Typeface
        get() = paint.typeface
        set(value) {
            paint.typeface = value
        }

    var layout: StaticLayout? = null

    private var desiredWidth = 0

    fun layout() {
        if (layout != null) {
            return
        }

        val text: CharSequence? = if (!TextUtils.isEmpty(text) && ellipsize) {
            TextUtils.ellipsize(text, paint, 1f * frame.width(), TextUtils.TruncateAt.END)
        } else this.text

        val layout: StaticLayout

        if (!TextUtils.isEmpty(text) && text != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                layout = StaticLayout.Builder.obtain(text, 0, text.length, paint, frame.width())
                        .setIncludePad(true)
                        .setLineSpacing(1f, 1f)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .build()
            }
            else {
                layout = StaticLayout(text, paint, frame.width(),
                        Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true)
            }

            for (line in 0 until layout.lineCount) {
                desiredWidth = Math.max(desiredWidth, layout.getLineWidth(line).toInt())
            }

            frame.setWidth(desiredWidth)
            frame.setHeight(layout.height)

            this.layout = layout
        } else {
            desiredWidth = 0
        }
    }

    override fun fitToSize(): Boolean {
        if (frame.width() == 0) {
            return false
        }

        layout = null

        layout()
        onBoundsChanged()
        return true
    }

    fun getLastLineBounds(): Rect {
        return Rect(0, getLastLineTop(), getLastLineWidth().toInt(), getLastLineBottom())
    }

    fun isSingleLine(): Boolean {
        return (layout?.lineCount ?: -1) == 1
    }

    fun getLastLineWidth(): Float {
        return layout?.let { it.getLineWidth(it.lineCount - 1) } ?: 0f
    }

    fun getLastLineTop(): Int {
        return layout?.let { it.getLineTop(it.lineCount - 1) } ?: 0
    }

    fun getLineHeight(): Int {
        return getLastLineBottom() - getLastLineTop()
    }

    fun getLastLineBottom(): Int {
        return layout?.let { it.getLineBottom(it.lineCount - 1) } ?: 0
    }

    fun getDesiredWidth(): Int {
        return desiredWidth
    }

    override fun draw(canvas: Canvas) {
        layout?.draw(canvas)
    }

    companion object {
        val DEFAULT_FONT: Typeface = Typeface.SANS_SERIF
    }
}
