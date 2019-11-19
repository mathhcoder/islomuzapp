package uz.islom.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import com.google.android.material.card.MaterialCardView
import me.tankery.lib.circularseekbar.CircularSeekBar
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Theme

class ZikrStateLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    private val progressSize by lazy {
        return@lazy (context.screenWidth()) / 2 - dp(16)
    }

    var theme = Theme.GREEN
        set(value) {
            setCardBackgroundColor(ColorStateList.valueOf(value.mainGradientStartColor))
            currentZikrTextView.setTextColor(value.secondaryColor)
            progressView.circleProgressColor = value.mainSeekBarProgressColor
            progressView.pointerColor = value.mainSeekBarDotColor
            progressView.circleColor = value.mainSeekBarFillColor
            progressInfoTextView.setTextColor(value.mainSeekBarProgressColor)
            field = value
        }

    @DrawableRes
    var currentZikrImage = 0
        set(value) {
            currentZikrImageView.setImageResource(value)
            field = value
        }

    var currentZikrText = ""
        set(value) {
            currentZikrTextView.text = value
            field = value
        }

    var progressInfo = ""
        set(value) {
            progressInfoTextView.text = value
            field = value
        }

    var zikrProgress = 0f
        set(value) {
            progressView.progress = value
            field = value
        }

    private val progressView by lazy {
        CircularSeekBar(context).apply {
            circleStrokeWidth = dp(4).toFloat()
            isEnabled = false
            max = 1f
        }
    }

    private val progressInfoTextView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER
            setTextSizeSp(32)
            setTypeface(typeface, Typeface.BOLD)
        }
    }

    private val currentZikrImageView by lazy {
        AppCompatImageView(context).apply {
            setPadding(dp(16))
        }
    }

    private val currentZikrTextView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER
            setTextSizeSp(16)
            setTypeface(typeface, Typeface.BOLD)

        }
    }

    init {

        isClickable = true
        radius = 0f


        addView(AppCompatImageView(context).apply {
            setBackgroundResource(R.drawable.img_zikr_back)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }, LayoutParams(full, full))

        addView(FrameLayout(context).apply {

            setPadding(dp(8), dp(8), dp(8), dp(8))

            addView(progressView, ViewGroup.LayoutParams(full, full))

            addView(LinearLayoutCompat(context).apply {

                orientation = LinearLayoutCompat.VERTICAL
                gravity = Gravity.CENTER

                addView(progressInfoTextView, LayoutParams(full, wrap))

            }, ViewGroup.LayoutParams(full, full))

        }, LayoutParams(progressSize, progressSize, Gravity.END))


        addView(LinearLayout(context).apply {

            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM
            weightSum = 1f

            addView(currentZikrImageView, LinearLayout.LayoutParams(full, 0, 1f))

            addView(currentZikrTextView, LinearLayout.LayoutParams(full, wrap).apply {
                bottomMargin = dp(8)
            })

        }, LayoutParams(full, full).apply {
            rightMargin = progressSize
        })


    }
}