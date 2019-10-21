package uz.islom.ui.cells

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.util.*

class SalatCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var salatName: String = ""
        set(value) {
            field = value
            salatNameView.text = value
        }

    var salatTime: String = ""
        set(value) {
            field = value
            salatTimeView.text = value
        }

    var notificationTypeIcon: Drawable? = null
        set(value) {
            field = value
            notificationIconView.setImageDrawable(value)
        }

    var theme: AppTheme = AppTheme.GREEN
        set(value) {
            field = value
            salatNameView.setTextColor(theme.tertiaryColor)
            salatTimeView.setTextColor(theme.tertiaryColor)
            notificationIconView.setColorFilter(theme.tertiaryColor)
            setCardBackgroundColor(theme.secondaryColor)
        }

    var isRead: Boolean = false

    private val notificationIconView = AppCompatImageView(context).apply {
        setPadding(dp(16), dp(16), dp(16), dp(16))
        setColorFilter(theme.tertiaryColor)
    }

    private val salatNameView = BaseTextView(context).apply {
        setTextColor(Color.BLACK)
        setTextSizeSp(16)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val salatTimeView = BaseTextView(context).apply {
        setTextColor(Color.BLACK)
        setTextSizeSp(16)
        setPadding(dp(16), 0, dp(16), 0)
        gravity = Gravity.CENTER_VERTICAL or Gravity.END
    }

    private val isReadView = MaterialCheckBox(context).apply {
        gravity = Gravity.CENTER
    }

    init {
        isClickable = true
        cardElevation = 1f

        setCardBackgroundColor(theme.secondaryColor)

        addView(notificationIconView, LayoutParams(dp(56), dp(56), Gravity.END))
        addView(isReadView, LinearLayout.LayoutParams(dp(56), dp(56)))
        addView(LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END
            weightSum = 1f

            addView(salatNameView, LinearLayout.LayoutParams(0, dp(56), 1f))
            addView(salatTimeView, LinearLayout.LayoutParams(wrap, dp(56)))

        }, LayoutParams(full, dp(56)).apply {
            rightMargin = dp(56)
            leftMargin = dp(56)
        })

    }
}