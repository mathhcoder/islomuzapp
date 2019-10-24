package uz.islom.ui.base

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

open class BaseTextView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : MaterialTextView(context, attributeSet, defStyleAttr) {

    init {
        //typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    }
}