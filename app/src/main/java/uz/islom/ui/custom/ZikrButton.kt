package uz.islom.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme

class ZikrButton @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr) {

    var theme = Theme.GREEN
        set(value) {
            field = value
            strokeDrawable.setStroke(dp(3), value.secondaryColor)
            solidDrawable.setColor(value.primaryColor)
            textView.setTextColor(value.mainSeekBarProgressColor)
        }

    var count = 0
        set(value) {
            textView.text = value.toString()
            field = value
        }

    private val textView by lazy {
        MaterialTextView(context).apply {
            gravity = Gravity.CENTER
            setTypeface(typeface, Typeface.BOLD)
            setTextSizeSp(40)
        }
    }

    private var strokeDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
    }

    private var solidDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
    }

    init {
        isClickable = true
        addView(FrameLayout(context).apply {
            background = LayerDrawable(arrayOf(solidDrawable, InsetDrawable(strokeDrawable, dp(3), dp(3), dp(3), dp(3))))
            addView(textView, LayoutParams(full, full))
        }, LayoutParams(full, full))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        strokeDrawable.cornerRadius = widthMeasureSpec.toFloat() / 2
        radius = widthMeasureSpec.toFloat() / 2
    }
}