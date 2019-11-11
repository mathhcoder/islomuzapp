package uz.islom.ui.cell

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp

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
            if (value != null) {
                navigateImageView.setImageDrawable(value)
                navigateImageView.visibility = View.VISIBLE
            }else{
                navigateImageView.visibility = View.GONE
            }
            field = value

        }

    var theme: Theme? = null
        set(value) {
            if (value != null) {
                optionNameView.setTextColor(value.tertiaryColor)
                optionImageView.setColorFilter(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
            field = value
        }

    private val optionImageView = AppCompatImageView(context).apply {
        setPadding(dp(16), dp(16), dp(16), dp(16))
    }

    private val optionNameView = BaseTextView(context).apply {
        setTextSizeSp(16)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val navigateImageView = AppCompatImageView(context).apply {
        setPadding(dp(20), dp(20), dp(20), dp(20))
    }


    init {
        isClickable = true
        cardElevation = 1f

        addView(optionImageView, LayoutParams(dp(64), dp(64)))
        addView(navigateImageView, LayoutParams(dp(64), dp(64), Gravity.END))
        addView(optionNameView, LayoutParams(full, dp(64)).apply {
            leftMargin = dp(72)
            rightMargin = dp(72)
        })

    }
}