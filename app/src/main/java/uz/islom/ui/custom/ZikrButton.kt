package uz.islom.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.textview.MaterialTextView
import uz.islom.ext.dp
import uz.islom.ext.setTextSizeSp
import uz.islom.model.dm.Theme

class ZikrButton @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : MaterialTextView(context, attributeSet, defStyleAttr) {

    var theme = Theme.GREEN
        set(value) {
            field = value
            strokeDrawable.setStroke(dp(3), value.secondaryColor)
            solidDrawable.setColor(value.primaryColor)
            setTextColor(value.mainSeekBarProgressColor)
        }

    var count = 0
        set(value) {
            text = value.toString()
            field = value
        }

    private var strokeDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
    }

    private var solidDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
    }

    init {
        background = LayerDrawable(arrayOf(solidDrawable, InsetDrawable(strokeDrawable, dp(3), dp(3), dp(3), dp(3))))
        gravity = Gravity.CENTER
        setTypeface(typeface, Typeface.BOLD)
        setTextSizeSp(40)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        strokeDrawable.cornerRadius = widthMeasureSpec.toFloat() / 2
    }
}