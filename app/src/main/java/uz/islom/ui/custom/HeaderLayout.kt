package uz.islom.ui.custom

import android.content.Context
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.setTextSizeSp
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme

class HeaderLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributes, defStyle) {

    var title: String? = ""
        set(value) {
            field = value
            titleTextView.text = value
        }

    var theme = Theme.GREEN
        set(value) {
            field = value
            titleTextView.setTextColor(value.secondaryColor)
            backButton.setColorFilter(value.secondaryColor)
            firstActionButton.setColorFilter(value.secondaryColor)
            secondActionButton.setColorFilter(value.secondaryColor)
            thirdActionButton.setColorFilter(value.secondaryColor)
            setBackgroundColor(value.toolBarColor)
        }

    private val backButton by lazy {
        BaseImageButton(context).apply {
            id = R.id.idBackButton
            setButtonPadding(dp(16))
            setImageResource(R.drawable.ic_arrow_left)
        }
    }

    private val titleTextView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER_VERTICAL
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSizeSp(18)
        }
    }

    private val firstActionButton by lazy {
        BaseImageButton(context).apply {
            visibility = View.GONE
            setButtonPadding(dp(8))
        }
    }

    private val secondActionButton by lazy {
        BaseImageButton(context).apply {
            visibility = View.GONE
            setButtonPadding(dp(8))
        }
    }

    private val thirdActionButton by lazy {
        BaseImageButton(context).apply {
            visibility = View.GONE
            setButtonPadding(dp(8))
        }
    }

    fun setUpBackAction(action: () -> (Unit)) {
        backButton.setOnClickListener { action() }
    }

    fun setUpFirstAction(@DrawableRes actionIcon: Int?, action: () -> (Unit)) {
        if (actionIcon != null) {
            firstActionButton.setImageResource(actionIcon)
            firstActionButton.visibility = View.VISIBLE
            firstActionButton.setOnClickListener {
                action()
            }
        } else {
            firstActionButton.visibility = View.GONE
        }
    }

    fun setUpSecondAction(@DrawableRes actionIcon: Int?, action: () -> (Unit)) {
        if (actionIcon != null) {
            secondActionButton.setImageResource(actionIcon)
            secondActionButton.visibility = View.VISIBLE
            secondActionButton.setOnClickListener {
                action()
            }
        } else {
            secondActionButton.visibility = View.GONE
        }
    }

    fun setUpThirdAction(@DrawableRes actionIcon: Int?, action: () -> (Unit)) {
        if (actionIcon != null) {
            thirdActionButton.setImageResource(actionIcon)
            thirdActionButton.visibility = View.VISIBLE
            thirdActionButton.setOnClickListener {
                action()
            }
        } else {
            thirdActionButton.visibility = View.GONE
        }
    }

    fun setFirstActionIcon(@DrawableRes actionIcon: Int) {
        firstActionButton.setImageResource(actionIcon)
    }

    fun setSecondActionIcon(@DrawableRes actionIcon: Int) {
        secondActionButton.setImageResource(actionIcon)
    }

    fun setThirdActionIcon(@DrawableRes actionIcon: Int) {
        thirdActionButton.setImageResource(actionIcon)
    }

    init {

        setBackgroundColor(theme.toolBarColor)

        addView(backButton, ViewGroup.LayoutParams(dp(56), dp(56)))

        addView(LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END or Gravity.CENTER_VERTICAL

            addView(titleTextView, LinearLayout.LayoutParams(0, full, 1f))
            addView(firstActionButton, LayoutParams(dp(40), dp(40)).apply {
                gravity = Gravity.CENTER_VERTICAL
            })
            addView(secondActionButton, LayoutParams(dp(40), dp(40)).apply {
                gravity = Gravity.CENTER_VERTICAL
            })
            addView(thirdActionButton, LayoutParams(dp(40), dp(40)).apply {
                gravity = Gravity.CENTER_VERTICAL
            })

        }, LinearLayout.LayoutParams(full, full).apply {
            leftMargin = dp(56)
            rightMargin = dp(8)
        })

    }

}
