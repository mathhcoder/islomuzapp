package uz.islom.ui.cell

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import uz.islom.ext.*
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView
import java.io.File

class AyatCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var fontFile: File? = null
        set(value) {
            arabicTextView.typeface = Typeface.createFromFile(value)
            field = value
        }

    var arabic: String? = ""
        set(value) {
            arabicTextView.text = value
            field = value
        }

    var meaning: String? = ""
        set(value) {
            meaningTextView.text = value
            field = value
        }

    var theme: Theme? = null
        set(value) {
            field = value
            if (value != null) {
                meaningTextView.setTextColor(value.tertiaryColor)
                arabicTextView.setTextColor(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
        }

    private val arabicTextView = BaseTextView(context).apply {
        setTextSizeSp(16)
    }

    private val meaningTextView = BaseTextView(context).apply {
        setTextSizeSp(14)
    }

    init {
        isClickable = true
        cardElevation = 1f

        addView(LinearLayout(context).apply {

            orientation = LinearLayout.VERTICAL

            addView(arabicTextView, LinearLayout.LayoutParams(full, wrap).apply {
                bottomMargin = dp(4)
            })
            addView(meaningTextView, LinearLayout.LayoutParams(full, wrap).apply {
                topMargin = dp(4)
            })

        }, LayoutParams(full, wrap).apply {
            leftMargin = dp(16)
            topMargin = dp(4)
            bottomMargin = dp(4)
            rightMargin = dp(16)
        })

    }
}