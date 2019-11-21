package uz.islom.ext

import android.content.Context
import android.view.View
import kotlin.math.min


fun Context.screenWidth(): Int {
    return resources.displayMetrics.widthPixels
}
fun View.screenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.screenHeight(): Int {
    return resources.displayMetrics.heightPixels
}
fun View.screenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.minScreenSize() = min(screenHeight(), screenWidth())
fun View.minScreenSize() = min(screenHeight(), screenWidth())

fun Context.headerSize() = screenWidth() / 2 - dp(16)
fun View.headerSize() = screenWidth() / 2 - dp(16)