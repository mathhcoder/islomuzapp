package uz.islom.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import uz.islom.ui.AppTheme
import uz.islom.ui.base.BaseTextView
import uz.islom.util.dp
import uz.islom.util.wrap

class BigImageButton @JvmOverloads constructor(
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
         //   textView.setTextColor(Color.WHITE)
          //  setCardBackgroundColor(theme.mainFunctionsColor)
        }

    private val imageView by lazy {
        return@lazy AppCompatImageView(context).apply {
            layoutParams = LayoutParams(dp(48), dp(48), Gravity.CENTER_HORIZONTAL)
                    .apply {
                        topMargin = dp(16)
                    }
            setColorFilter(theme.mainFunctionsColor)
        }
    }

    private val textView by lazy {
        return@lazy BaseTextView(context).apply {
            layoutParams = LayoutParams(wrap, wrap, Gravity.CENTER_HORIZONTAL).apply {
                topMargin = dp(72)
                bottomMargin = dp(8)
            }
            //setTextColor(theme.mainFunctionsTextColor)
            textSize = 12f
            gravity = Gravity.CENTER_HORIZONTAL
        }
    }

    init {
        isClickable = true
        radius = dp(6).toFloat()

        addView(imageView)
        addView(textView)

    }

    override fun isClickable() = true

}