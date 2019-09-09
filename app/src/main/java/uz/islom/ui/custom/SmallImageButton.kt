package uz.islom.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import uz.islom.ui.AppTheme
import uz.islom.ui.base.BaseTextView
import uz.islom.util.dp
import uz.islom.util.wrap

class SmallImageButton @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attributes, defStyle) {

    var imageRes: Int = 0
        set(value) {
            field = value
            imageView.setImageResource(imageRes)
        }

    var text: String = ""
        set(value) {
            field = value
            textView.text = value
        }

    var theme: AppTheme = AppTheme.GREEN
        set(value) {
            field = value
            textView.setTextColor(Color.WHITE)
            setCardBackgroundColor(theme.mainFunctionsColor)
        }

    private val imageView = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(dp(40), dp(40))
        setPadding(dp(8), dp(8), dp(8), dp(8))
    }

    private val textView = BaseTextView(context).apply {
        layoutParams = LayoutParams(wrap, wrap).apply {
            leftMargin = dp(48)
            rightMargin = dp(8)
            gravity = Gravity.CENTER_VERTICAL
        }
        setTextColor(Color.WHITE)
    }

    init {
        addView(imageView)
        addView(textView)
        setCardBackgroundColor(theme.mainFunctionsColor)
        radius = dp(4).toFloat()
    }
}