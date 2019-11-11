package uz.islom.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import uz.islom.R
import uz.islom.model.dm.Theme
import uz.islom.ext.full

class FooterLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributes, defStyle) {

    var theme = Theme.GREEN
        set(value) {
            field = value
            findViewById<AppCompatImageView>(R.id.idImageView)?.setColorFilter(value.footerColor)
        }

    init {
        addView(AppCompatImageView(context).apply {
            id = R.id.idImageView
            setImageResource(R.drawable.img_footer)
            scaleType = ImageView.ScaleType.FIT_XY
        }, ViewGroup.LayoutParams(full, full))

    }
}
