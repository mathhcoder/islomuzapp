package uz.islom.ui.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.R
import uz.islom.ui.util.AppTheme
import uz.islom.ui.base.BaseTextView
import uz.islom.android.drawable
import uz.islom.ui.util.dp
import uz.islom.ui.util.full
import uz.islom.ui.util.setTextSizeSp

class OptionCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var optionImage: Drawable? = null
        set(value) {
            field = value
            optionImageView.setImageDrawable(value)
        }


    var optionName: String = ""
        set(value) {
            field = value
            optionNameView.text = optionName
        }

    var navigateImage: Drawable? = null
        set(value) {
            field = value
            navigateImageView.setImageDrawable(value)
        }

    var theme: AppTheme = AppTheme.GREEN
        set(value) {
            field = value
            optionNameView.setTextColor(theme.tertiaryColor)
            optionImageView.setColorFilter(theme.tertiaryColor)
            setCardBackgroundColor(theme.secondaryColor)
        }

    private val optionImageView = AppCompatImageView(context).apply {
        setPadding(dp(16), dp(16), dp(16), dp(16))
        setColorFilter(theme.tertiaryColor)
    }

    private val optionNameView = BaseTextView(context).apply {
        setTextColor(theme.tertiaryColor)
        setTextSizeSp(16)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val navigateImageView = AppCompatImageView(context).apply {
        setPadding(dp(20), dp(20), dp(20), dp(20))
        setColorFilter(theme.tertiaryColor)
    }


    init {
        isClickable = true
        cardElevation = 1f

        setCardBackgroundColor(theme.secondaryColor)

        addView(optionImageView, LayoutParams(dp(64), dp(64)))
        addView(navigateImageView, LayoutParams(dp(64), dp(64), Gravity.END))
        addView(optionNameView, LayoutParams(full, dp(64)).apply {
            leftMargin = dp(72)
            rightMargin = dp(72)
        })

    }
}