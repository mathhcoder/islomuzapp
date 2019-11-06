package uz.islom.ui.cell

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.enums.ThemeType
import uz.islom.ui.base.BaseTextView

class AsmaUlHusnaCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var nameLocal: String? = ""
        set(value) {
            field = value
            nameInLocal.text = value
        }

    var nameArabic: String? = ""
        set(value) {
            field = value
            nameInArabic.text = nameArabic
        }

    var theme: ThemeType = ThemeType.GREEN
        set(value) {
            field = value
            nameInArabic.setTextColor(theme.tertiaryColor)
            nameInLocal.setTextColor(theme.tertiaryColor)
            setCardBackgroundColor(theme.secondaryColor)
        }

    private val nameInArabic = BaseTextView(context).apply {
        setTextColor(theme.tertiaryColor)
        typeface = font(R.font.scheherazade_normal)
        setTextSizeSp(28)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val nameInLocal = BaseTextView(context).apply {
        setTextColor(theme.tertiaryColor)
        setTextSizeSp(18)
        gravity = Gravity.CENTER_VERTICAL
    }


    init {
        isClickable = true
        cardElevation = 1f

        setCardBackgroundColor(theme.secondaryColor)

        addView(LinearLayout(context).apply {

            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END

            addView(nameInLocal, LinearLayout.LayoutParams(0, full, 1f).apply {
                leftMargin = dp(16)
                gravity = Gravity.CENTER_VERTICAL
            })
            addView(nameInArabic, LinearLayout.LayoutParams(wrap, wrap).apply {
                leftMargin = dp(16)
                rightMargin = dp(16)
                gravity = Gravity.CENTER_VERTICAL
            })

        })

    }
}