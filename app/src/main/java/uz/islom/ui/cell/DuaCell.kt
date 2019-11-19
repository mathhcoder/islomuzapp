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

class DuaCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var order : Long? = 0
        set(value) {
            field = value
            orderView.text = String.format("%d.",value)
        }

    var nameLocal: String? = ""
        set(value) {
            field = value
            nameInLocalView.text = value
        }

    var description: String? = ""
        set(value) {
            field = value
            descriptionView.text = value
        }

    var theme: Theme? = null
        set(value) {
            field = value
            if(value!=null){
                nameInLocalView.setTextColor(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
        }

    private val orderView = BaseTextView(context).apply {
        setTextSizeSp(20)
        gravity = Gravity.CENTER
    }

    private val nameInLocalView = BaseTextView(context).apply {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setTextSizeSp(16)
    }

    private val descriptionView = BaseTextView(context).apply {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setTextSizeSp(14)
    }


    init {
        isClickable = true
        cardElevation = 1f

        addView(orderView, LayoutParams(dp(72), dp(72)))

        addView(LinearLayout(context).apply {

            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL

                addView(nameInLocalView,LinearLayout.LayoutParams(full,wrap).apply {
                    bottomMargin = dp(1)
                })
                addView(descriptionView,LinearLayout.LayoutParams(full,wrap).apply {
                    topMargin = dp(1)
                })

            }, LinearLayout.LayoutParams(0, full, 1f).apply {
                gravity = Gravity.CENTER_VERTICAL
            })

        }, LayoutParams(full,dp(72)).apply {
            leftMargin = dp(72)
        })

    }
}