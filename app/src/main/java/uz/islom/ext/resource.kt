package uz.islom.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor


/** Color */

fun View.colour(@ColorRes res: Int): Int {
    return context.colour(res)
}

fun Fragment.colour(@ColorRes res: Int): Int {
    return requireContext().colour(res)
}

fun Context.colour(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

/** Drawable */

fun View.drawable(@DrawableRes res: Int): Drawable? {
    return context.drawable(res)
}

fun Fragment.drawable(@DrawableRes res: Int): Drawable? {
    return requireContext().drawable(res)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return AppCompatResources.getDrawable(this, res)
}

fun Fragment.drawable(@DrawableRes res: Int, tintColor: Int): Drawable {
    return requireContext().drawable(res, tintColor)
}

fun View.drawable(@DrawableRes res: Int, tintColor: Int): Drawable {
    return context.drawable(res, tintColor)
}

fun Context.drawable(@DrawableRes res: Int, tintColor: Int): Drawable {
    val drawable = DrawableCompat.wrap(AppCompatResources.getDrawable(this, res)!!)
    DrawableCompat.setTint(drawable, tintColor)
    return drawable
}

/** String */

fun View.string(@StringRes res: Int): String? {
    return context.string(res)
}

fun Fragment.string(@StringRes res: Int): String? {
    return requireContext().string(res)
}

fun Context.string(@StringRes res: Int): String? {
    return this.resources.getString(res)
}

fun View.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}


fun Context.font(@FontRes res: Int): Typeface? {
    return ResourcesCompat.getFont(this, res)
}

fun View.font(@FontRes res: Int): Typeface? {
    return ResourcesCompat.getFont(context, res)
}

fun Fragment.font(@FontRes res: Int): Typeface? {
    return ResourcesCompat.getFont(requireContext(), res)
}


