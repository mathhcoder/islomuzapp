package uz.islom.ui.cell

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView
import uz.islom.ext.dp
import uz.islom.ext.setTextSizeSp
import uz.islom.ext.wrap

class FunctionCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var imageRes: Int = 0
        set(value) {
            field = value
            imageView.setImageResource(imageRes)
        }

    var textRes: Int = 0
        set(value) {
            field = value
            textView.setText(value)
        }

    var theme: Theme? = null
        set(value) {
            if(value!=null) {
                imageView.setColorFilter(value.primaryColor)
                textView.setTextColor(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
            field = value
        }

    private val imageView by lazy {
        return@lazy AppCompatImageView(context)
    }

    private val textView by lazy {
        return@lazy BaseTextView(context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setTextSizeSp(12)
        }
    }

    init {
        isClickable = true
        radius = dp(6).toFloat()

        addView(imageView, LayoutParams(dp(48), dp(48), Gravity.CENTER_HORIZONTAL).apply {
            topMargin = dp(16)
        })

        addView(textView, LayoutParams(wrap, wrap, Gravity.CENTER_HORIZONTAL).apply {
            topMargin = dp(72)
            bottomMargin = dp(8)
        })

    }

    override fun isClickable() = true

}