package uz.islom.ui.cells

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.ui.util.AppTheme
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.util.dp
import uz.islom.ui.util.setTextSizeSp
import uz.islom.ui.util.wrap

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

    var theme: AppTheme = AppTheme.GREEN
        set(value) {
            field = value
            imageView.setColorFilter(theme.primaryColor)
            textView.setTextColor(theme.tertiaryColor)
            setCardBackgroundColor(theme.secondaryColor)
        }

    private val imageView by lazy {
        return@lazy AppCompatImageView(context).apply {
            setColorFilter(theme.primaryColor)
        }
    }

    private val textView by lazy {
        return@lazy BaseTextView(context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setTextColor(theme.tertiaryColor)
            setTextSizeSp(12)
        }
    }

    init {
        isClickable = true
        radius = dp(6).toFloat()

        setCardBackgroundColor(theme.secondaryColor)

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