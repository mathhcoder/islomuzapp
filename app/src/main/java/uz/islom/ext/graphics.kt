package uz.islom.ext

import android.graphics.Canvas
import android.graphics.Picture
import android.graphics.Rect
import android.graphics.RectF


inline fun Picture.draw(width: Int, height: Int, performDrawing: Canvas.() -> Unit) {
    val canvas = beginRecording(width, height)
    canvas.performDrawing()
    endRecording()
}

inline fun Canvas.translated(bounds: Rect, performDrawing: Canvas.() -> Unit) {
    stateIndependent {
        clipRect(bounds)
        translated(bounds.left, bounds.top, performDrawing)
    }
}

inline fun Canvas.translated(dx: Number, dy: Number, performDrawing: Canvas.() -> Unit) {
    stateIndependent {
        translate(dx.toFloat(), dy.toFloat())
        performDrawing()
    }
}

inline fun Canvas.stateIndependent(draw: Canvas.() -> Unit) {
    val index = save()
    draw()
    restoreToCount(index)
}

/** == Rectangles Extension == */

fun Rect.setHeight(height: Int) {
    set(left, top, right, top + height)
}

fun Rect.setWidth(width: Int) {
    set(left, top, left + width, bottom)
}

fun Rect.setSquareSize(spaceAlloc: Int) {
    set(left, top, left + spaceAlloc, bottom + spaceAlloc)
}

fun Rect.half(second: Boolean = false, width: Float = 1f, height: Float = 1f): Rect {
    val offset = if (second) 1 else 0
    return Rect(0, 0, (width() * width).toInt(), (height() * height).toInt()).apply {
        offset(
                ((width() - (width() * width)) * offset).toInt(),
                ((height() - (height() * height)) * offset).toInt()
        )
    }
}

fun RectF.setHeight(height: Float) {
    set(left, top, right, top + height)
}

fun RectF.setWidth(width: Float) {
    set(left, top, left + width, bottom)
}

operator fun Rect.minus(other: Rect): Rect {
    return Rect(
            this.left - other.left,
            this.top - other.top,
            this.right - other.right,
            this.bottom - other.bottom
    )
}

fun RectF.half(second: Boolean = false, width: Float = 1f, height: Float = 1f): RectF {
    val offset = if (second) 1 else 0
    return RectF(0f, 0f, width() * width, height() * height).apply {
        offset(
                (width() - (width() * width)) * offset,
                (height() - (height() * height)) * offset
        )
    }
}
