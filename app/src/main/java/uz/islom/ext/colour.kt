package uz.islom.ext

import android.graphics.Color
import kotlin.math.roundToInt

fun Int.getColorWithAlpha(alpha: Float): Int {
    val ratio = (Color.alpha(this) * alpha).roundToInt()
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)
    return Color.argb(ratio, r, g, b)
}