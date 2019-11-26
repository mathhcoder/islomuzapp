package uz.islom.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.view.setPadding
import com.google.android.material.card.MaterialCardView
import uz.islom.R
import uz.islom.ext.font
import uz.islom.ext.setTextSizeSp
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme
import kotlin.math.max
import kotlin.math.min


class AsmaUlHusnaLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var theme = Theme.GREEN
        set(value) {
            textView.setTextColor(value.secondaryColor)
            field = value
        }

    var text: String
        set(value) {
            textView.text = value
        }
        get() {
            return textView.text.toString()
        }

    private val textView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER
            setTextSizeSp(64)
            setPadding(64)
            setBackgroundResource(R.drawable.bg_asma_ul_husna)
            typeface = font(R.font.scheherazade_normal)
            setTypeface(typeface, Typeface.BOLD)
        }
    }

    init {
        addView(textView, LinearLayout.LayoutParams(wrap, wrap))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = 100
        val desiredHeight = 100

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> max(desiredWidth, widthSize)
            else -> desiredWidth
        }

        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> max(desiredHeight, heightSize)
            else -> //Be whatever you want
                desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }
}