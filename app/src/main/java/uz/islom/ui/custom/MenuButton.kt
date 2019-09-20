package uz.islom.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.R
import uz.islom.ui.AppTheme
import uz.islom.ui.base.BaseTextView
import uz.islom.util.*

class MenuButton @JvmOverloads constructor(
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
            textView.setText(textRes)
        }

    var theme: AppTheme = AppTheme.GREEN
        set(value) {
            field = value
            textView.setTextColor(theme.tertiaryColor)
            imageView.setColorFilter(theme.tertiaryColor)
            setCardBackgroundColor(theme.secondaryColor)
        }

    private val imageView = AppCompatImageView(context).apply {
        setPadding(dp(16), dp(16), dp(16), dp(16))
        setColorFilter(theme.tertiaryColor)
    }

    private val textView = BaseTextView(context).apply {
        setTextColor(theme.tertiaryColor)
        setTextSizeSp(16)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val iconView = AppCompatImageView(context).apply {
        setImageDrawable(drawable(R.drawable.ic_arrow_right))
        setPadding(dp(20), dp(20), dp(20), dp(20))
        setColorFilter(theme.tertiaryColor)
    }


    init {
        isClickable = true
        cardElevation = 1f

        setCardBackgroundColor(theme.secondaryColor)

        addView(imageView, LayoutParams(dp(64), dp(64)))
        addView(iconView, LayoutParams(dp(64), dp(64), Gravity.END))
        addView(textView, LayoutParams(full, dp(64)).apply {
            leftMargin = dp(72)
            rightMargin = dp(72)
        })

    }
}