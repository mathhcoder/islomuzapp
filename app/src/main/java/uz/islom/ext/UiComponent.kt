package uz.islom.ext

import android.graphics.Canvas
import android.graphics.Rect
import androidx.annotation.WorkerThread

open class UiComponent {

    val frame = Rect()

    fun fitToSize(width: Int, height: Int): Boolean {
        frame.setWidth(width)
        frame.setHeight(height)
        return fitToSize()
    }

    protected open fun fitToSize(): Boolean {
        return false
    }

    open fun onBoundsChanged() {

    }

    @WorkerThread
    fun dispatchDraw(canvas: Canvas) {
        if (frame.isEmpty) {
            return
        }

        val state = canvas.save()
        if (frame.left != 0 || frame.top != 0) {
            canvas.translate(frame.left * 1f, frame.top * 1f)
        }

        canvas.clipRect(0, 0, frame.width(), frame.height())
        draw(canvas)
        canvas.restoreToCount(state)
    }

    @WorkerThread
    open fun draw(canvas: Canvas) {

    }
}
