package uz.islom.ui.cell

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView

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

    var theme: Theme? = null
        set(value) {
            field = value
            if(value!=null){
                salatNameView.setTextColor(value.tertiaryColor)
                salatTimeView.setTextColor(value.tertiaryColor)
                notificationIconView.setColorFilter(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
        }

    var isRead: Boolean = false

    private val notificationIconView = AppCompatImageView(context).apply {
        id = R.id.idImageView
        setPadding(dp(16), dp(16), dp(16), dp(16))
    }

    private val salatNameView = BaseTextView(context).apply {
        id = R.id.idTitleTextView
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