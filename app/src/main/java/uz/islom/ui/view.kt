package uz.islom.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup

fun View.dp(dp: Int): Int {
    return dp * (resources.displayMetrics?.density ?: 0f).toInt()
}

fun Context.dp(dp: Int): Int {
    return dp * (resources.displayMetrics?.density ?: 0f).toInt()
}

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = if (this == 0) false else true

const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT
const val full = ViewGroup.LayoutParams.MATCH_PARENT

fun View.screenWidth() = resources.displayMetrics.widthPixels