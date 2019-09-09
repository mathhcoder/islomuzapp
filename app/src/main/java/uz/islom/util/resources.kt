package uz.islom.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun View.colour(@ColorRes res: Int): Int {
    return context.colour(res)
}

fun Fragment.colour(@ColorRes res: Int): Int {
    return requireContext().colour(res)
}

fun Context.colour(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun View.drawable(@DrawableRes res: Int): Drawable? {
    return context.drawable(res)
}

fun Fragment.drawable(@DrawableRes res: Int): Drawable? {
    return requireContext().drawable(res)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return AppCompatResources.getDrawable(this, res)
}

fun View.string(@StringRes res: Int): String? {
    return context.string(res)
}

fun Fragment.string(@StringRes res: Int): String? {
    return requireContext().string(res)
}

fun Context.string(@StringRes res: Int): String? {
    return this.resources.getString(res)
}



