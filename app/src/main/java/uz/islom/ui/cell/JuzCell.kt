package uz.islom.ui.cell

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Theme
import uz.islom.ui.custom.BaseTextView

class JuzCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var title: String? = ""
        set(value) {
            field = value
            nameView.text = value
        }

    var subtitle: String? = ""
        set(value) {
            field = value
            ayatView.text = value
        }

    var theme: Theme? = null
        set(value) {
            field = value
            if (value != null) {
                ayatView.setTextColor(value.tertiaryColor)
                nameView.setTextColor(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
        }

    private val nameView = BaseTextView(context).apply {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setTextSizeSp(16)
    }

    private val ayatView = BaseTextView(context).apply {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setTextSizeSp(14)
    }


    init {
        isClickable = true
        cardElevation = 1f

        addView(LinearLayout(context).apply {

            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL

                addView(nameView, LinearLayout.LayoutParams(full, wrap).apply {
                    bottomMargin = dp(1)
                })
                addView(ayatView, LinearLayout.LayoutParams(full, wrap).apply {
                    topMargin = dp(1)
                })

            }, LinearLayout.LayoutParams(0, full, 1f).apply {
                gravity = Gravity.CENTER_VERTICAL
            })

        }, LayoutParams(full, dp(56)).apply {
            leftMargin = dp(16)
            rightMargin = dp(16)
        })

    }
}