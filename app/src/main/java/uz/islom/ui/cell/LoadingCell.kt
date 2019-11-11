package uz.islom.ui.cell

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.view.setPadding
import com.google.android.material.card.MaterialCardView
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme

class LoadingCell @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var theme: Theme? = null
        set(value) {
            field = value
            if (value != null) {
                loadingView.indeterminateTintList = ColorStateList.valueOf(value.tertiaryColor)
                setCardBackgroundColor(value.secondaryColor)
            }
        }

    private val loadingView = MaterialProgressBar(context).apply {
        setPadding(dp(16))
        isIndeterminate = true
    }

    init {
        isClickable = true
        cardElevation = 1f

        addView(loadingView, LayoutParams(wrap, full, Gravity.CENTER))

    }
}