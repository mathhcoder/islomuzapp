package uz.islom.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp
import uz.islom.model.dm.Theme

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

    var theme = Theme.GREEN
        set(value) {
            field = value
            findViewById<BaseTextView>(R.id.idTextView)?.setTextColor(value.secondaryColor)
            findViewById<BaseImageButton>(R.id.idBackButton)?.setColorFilter(value.secondaryColor)
            findViewById<BaseImageButton>(R.id.idActionButton)?.setColorFilter(value.secondaryColor)
            setBackgroundColor(value.toolBarColor)
        }

    @DrawableRes
    var actionIcon : Int? = null
        set(value) {
            field = value
            if (value != null){
                findViewById<BaseImageButton>(R.id.idActionButton)?.let {
                    it.setImageResource(value)
                    it.visibility = View.VISIBLE
                }
            }else{
                findViewById<BaseImageButton>(R.id.idActionButton)?.visibility = View.GONE
            }
        }

    var onBackListener: OnBackClickListener? = null
    var onActionListener: OnActionClickListener? = null

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
            rightMargin = dp(56)
        })

        addView(BaseImageButton(context).apply {
            visibility = View.GONE
            id = R.id.idActionButton
            setButtonPadding(dp(16))
            setColorFilter(theme.secondaryColor)
            setOnClickListener {
                onActionListener?.onActionClicked(this)
            }
        }, LayoutParams(dp(56), dp(56), Gravity.END))

    }

    interface OnBackClickListener {
        fun onBackClicked()
    }

    interface OnActionClickListener {
        fun onActionClicked(baseImageButton: BaseImageButton)
    }
}
