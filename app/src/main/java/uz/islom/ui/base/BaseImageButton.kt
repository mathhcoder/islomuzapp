package uz.islom.ui.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import uz.islom.R
import uz.islom.util.colour
import uz.islom.util.drawable


class BaseImageButton @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageButton(context, attributeSet, defStyleAttr) {

    fun setButtonPadding(value: Int) {
        setPadding(value, value, value, value)
    }

    fun setImageResources(@DrawableRes drawableRes: Int,color: Int) {
        setImageDrawable(drawable(drawableRes,color))
    }


    init {
        if (Build.VERSION.SDK_INT >= 21) {
            background = RippleDrawable(ColorStateList(arrayOf(intArrayOf()), intArrayOf(colour(R.color.colorPrimaryDark))), null, null)
        }
    }
}