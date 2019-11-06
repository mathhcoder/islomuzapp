package uz.islom.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.R
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.model.enums.ThemeType
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp

class HeaderLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributes, defStyle) {

    var title: String? = ""
        set(value) {
            field = value
            findViewById<BaseTextView>(R.id.idTextView)?.text = value
        }

    var theme = ThemeType.GREEN
        set(value) {
            field = value
            findViewById<BaseTextView>(R.id.idTextView)?.setTextColor(value.secondaryColor)
            findViewById<BaseImageButton>(R.id.idBackButton)?.setColorFilter(value.secondaryColor)
            setBackgroundColor(value.toolBarColor)
        }

    var onBackListener: OnBackClickListener? = null

    init {

        setBackgroundColor(theme.toolBarColor)

        addView(BaseImageButton(context).apply {
            id = R.id.idBackButton
            setButtonPadding(dp(16))
            setColorFilter(theme.secondaryColor)
            setImageResource(R.drawable.ic_arrow_left)
            setOnClickListener {
                onBackListener?.onBackClicked()
            }
        }, ViewGroup.LayoutParams(dp(56), dp(56)))

        addView(BaseTextView(context).apply {
            id = R.id.idTextView
            gravity = Gravity.CENTER_VERTICAL
            setTextSizeSp(18)
            setTextColor(theme.secondaryColor)
        }, LayoutParams(full, full).apply {
            leftMargin = dp(72)
            rightMargin = dp(16)
        })

    }

    interface OnBackClickListener {
        fun onBackClicked()
    }
}
