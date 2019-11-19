package uz.islom.ui.custom

import android.content.Context
import android.content.res.ColorStateList
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
import com.google.android.material.card.MaterialCardView
import me.tankery.lib.circularseekbar.CircularSeekBar
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.DateState
import uz.islom.model.dm.Theme

class SalatStateLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    var theme = Theme.GREEN
        set(value) {
            field = value

            background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(value.mainGradientStartColor, value.mainGradientMiddleColor, value.mainGradientEndColor)).apply {
                gradientRadius = -20f
            }

            topLineView.background = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(value.mainLineColor, value.mainLineColor.getColorWithAlpha(0.2f)))
            bottomLineView.background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(value.mainLineColor, value.mainLineColor.getColorWithAlpha(0.2f)))
            middleLineView.setBackgroundColor(value.mainLineColor)

            grigorianDateTextView.setTextColor(value.secondaryColor)
            hijriDateTextView.setTextColor(value.secondaryColor)

            nextSalatLayout.setCardBackgroundColor(value.mainIconsDarkColor)
            nextSalatTextView.setTextColor(value.secondaryColor)
            notificationTextView.setTextColor(value.secondaryColor)
            notificationImageButton.setColorFilter(value.mainSeekBarProgressColor)
            notificationImageButton.supportBackgroundTintList = ColorStateList.valueOf(value.mainIconsDarkColor)

            currentSalatNameTextView.setTextColor(value.secondaryColor)
            currentTimeClockView.setTextColor(value.mainSeekBarProgressColor)
            remainingTimeForNextSalatTextView.setTextColor(value.secondaryColor)
            progressView.let {
                it.circleProgressColor = value.mainSeekBarProgressColor
                it.pointerColor = value.mainSeekBarDotColor
                it.pointerHaloColor = value.mainSeekBarDotColor
                it.circleColor = value.mainSeekBarFillColor
            }

        }

    var grigorianDate: String = ""
        set(value) {
            grigorianDateTextView.text = value
            field = value
        }

    var hijriDate: String = ""
        set(value) {
            hijriDateTextView.text = value
            field = value
        }

    var currentSalatName = ""
        set(value) {
            currentSalatNameTextView.text = value
            field = value
        }

    var salatProgress = 0f
        set(value) {
            progressView.progress = salatProgress
            field = value
        }

    @DrawableRes
    var notificationImageResource = 0
        set(value) {
            notificationImageButton.setImageResource(value)
            field = value
        }

    var nextSalatName = ""
        set(value) {
            nextSalatTextView.text = value
            field = value
        }

    var remainingTime = ""
        set(value) {
            remainingTimeForNextSalatTextView.text = value
            field = value
        }




    private val progressSize by lazy {
        return@lazy (context.screenWidth()) / 2 - dp(16)
    }

    private val topLineView by lazy {
        View(context)
    }

    private val middleLineView by lazy {
        View(context)
    }

    private val bottomLineView by lazy {
        View(context)
    }

    private val progressView by lazy {
        CircularSeekBar(context).apply {
            circleStrokeWidth = dp(4).toFloat()
            isEnabled = false
            max = 1f
        }
    }

    private val currentSalatNameTextView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER
            setTextSizeSp(16)
            setTypeface(typeface, Typeface.BOLD)
        }
    }

    private val currentTimeClockView by lazy {
        if (Build.VERSION.SDK_INT >= 17) {
            TextClock(context).apply {
                gravity = Gravity.CENTER
                typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                format12Hour = null
                setTextSizeSp(32)
            }
        } else {
            DigitalClock(context).apply {
                gravity = Gravity.CENTER
                setTextSizeSp(32)
                setTypeface(typeface, Typeface.BOLD)
            }
        }
    }

    private val remainingTimeForNextSalatTextView by lazy {
        BaseTextView(context).apply {
            gravity = Gravity.CENTER
            setTextSizeSp(14)
        }
    }

    private val grigorianDateTextView by lazy {
        BaseTextView(context).apply {
            setTextSizeSp(14)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    private val hijriDateTextView by lazy {
        BaseTextView(context).apply {
            alpha = 0.5f
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSizeSp(12)
        }
    }

    private val nextSalatTextView by lazy {
        TrimmedTextView(context).apply {
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setPadding(dp(16), dp(4), dp(16), dp(4))
            setTypeface(typeface, Typeface.BOLD)
            setTextSizeSp(14)
        }
    }

    private val nextSalatLayout by lazy {
        CardView(context).apply {

            addView(nextSalatTextView, LayoutParams(wrap, wrap, Gravity.CENTER))

            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    radius = measuredHeight.toFloat() / 2
                }
            })

        }
    }

    private val notificationTextView by lazy {
        BaseTextView(context).apply {
            text = string(R.string.notify)
            gravity = Gravity.CENTER_VERTICAL
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    private val notificationImageButton by lazy {
        BaseImageButton(context).apply {
            setButtonPadding(dp(8))
        }
    }

    init {

        isClickable = true
        radius = 0f

        GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(theme.mainGradientStartColor, theme.mainGradientMiddleColor, theme.mainGradientEndColor)).apply {
            gradientRadius = -20f
        }

        addView(topLineView, LayoutParams(dp(2), dp(16)).apply {
            leftMargin = dp(19)
            topMargin = dp(16)
            bottomMargin = dp(16)
        })

        addView(middleLineView, LayoutParams(dp(2), full).apply {
            leftMargin = dp(19)
            topMargin = dp(32)
            bottomMargin = dp(32)
        })

        addView(bottomLineView, LayoutParams(dp(2), dp(16), Gravity.BOTTOM).apply {
            leftMargin = dp(19)
            topMargin = dp(16)
            bottomMargin = dp(16)
        })

        addView(FrameLayout(context).apply {

            setPadding(dp(8), dp(8), dp(8), dp(8))

            addView(progressView, ViewGroup.LayoutParams(full, full))

            addView(LinearLayoutCompat(context).apply {

                orientation = LinearLayoutCompat.VERTICAL
                gravity = Gravity.CENTER

                addView(currentSalatNameTextView, LayoutParams(full, wrap))

                addView(currentTimeClockView, LayoutParams(full, wrap))

                addView(remainingTimeForNextSalatTextView, LayoutParams(full, wrap))

            }, ViewGroup.LayoutParams(full, full))

        }, LayoutParams(progressSize, progressSize, Gravity.END))

        addView(LinearLayout(context).apply {

            setPadding(dp(8), dp(16), 0, dp(16))
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            weightSum = 3f

            addView(FrameLayout(context).apply {

                addView(FrameLayout(context).apply {

                    addView(AppCompatImageView(context).apply {
                        setImageResource(R.drawable.ic_circle_gradient)
                    }, LayoutParams(dp(24), dp(24)))

                    addView(LinearLayout(context).apply {

                        orientation = LinearLayout.VERTICAL

                        addView(grigorianDateTextView, LinearLayout.LayoutParams(full, wrap))

                        addView(hijriDateTextView, LinearLayout.LayoutParams(full, wrap))

                    }, LayoutParams(full, wrap).apply {
                        leftMargin = dp(28)
                    })

                }, LayoutParams(wrap, wrap, Gravity.BOTTOM))

            }, LinearLayout.LayoutParams(wrap, 0, 1f))

            addView(FrameLayout(context).apply {

                addView(AppCompatImageView(context).apply {
                    setImageResource(R.drawable.ic_circle_gradient)
                }, LayoutParams(dp(24), dp(24), Gravity.CENTER_VERTICAL))

                addView(nextSalatLayout, LayoutParams(wrap, wrap, Gravity.CENTER_VERTICAL).apply {
                    leftMargin = dp(28)
                })

            }, LinearLayout.LayoutParams(full, 0, 1f))

            addView(LinearLayout(context).apply {

                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL

                addView(AppCompatImageView(context).apply {
                    setImageResource(R.drawable.ic_circle_gradient)
                }, LinearLayout.LayoutParams(dp(24), dp(24)))

                addView(notificationTextView, LinearLayout.LayoutParams(wrap, wrap).apply {
                    leftMargin = dp(4)
                })

                addView(notificationImageButton, LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                    leftMargin = dp(8)
                })

            }, LinearLayout.LayoutParams(full, 0, 1f))

        }, LayoutParams(full, progressSize).apply {
            rightMargin = progressSize
        })

    }
}