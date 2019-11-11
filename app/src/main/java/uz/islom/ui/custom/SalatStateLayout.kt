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
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import me.tankery.lib.circularseekbar.CircularSeekBar
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Theme

class SalatStateLayout @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attributes, defStyle) {

    private val progressSize by lazy {
        return@lazy (context.screenWidth()) / 2 - dp(16)
    }

    var theme = Theme.GREEN
        set(value) {
            field = value

            background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(value.mainGradientStartColor, value.mainGradientMiddleColor, value.mainGradientEndColor)).apply {
                gradientRadius = -20f
            }

            findViewById<View>(R.id.idTopLineView)?.background = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(value.mainLineColor, value.mainLineColor.getColorWithAlpha(0.2f)))
            findViewById<View>(R.id.idBottomLineView)?.background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(value.mainLineColor, value.mainLineColor.getColorWithAlpha(0.2f)))
            findViewById<View>(R.id.idMiddleLineView)?.setBackgroundColor(value.mainLineColor)

            findViewById<BaseTextView>(R.id.idGrigorianView)?.setTextColor(value.secondaryColor)
            findViewById<BaseTextView>(R.id.idHijriDate)?.setTextColor(value.secondaryColor)

            findViewById<CardView>(R.id.idNextSalatLayout)?.setCardBackgroundColor(value.mainIconsDarkColor)
            findViewById<TextView>(R.id.idNextSalatView)?.setTextColor(value.secondaryColor)
            findViewById<TextView>(R.id.idNotificationView)?.setTextColor(value.secondaryColor)
            findViewById<BaseImageButton>(R.id.idNotificationImageView)?.setColorFilter(value.mainSeekBarProgressColor)
            findViewById<BaseImageButton>(R.id.idNotificationImageView)?.supportBackgroundTintList = ColorStateList.valueOf(value.mainIconsDarkColor)

            findViewById<TextView>(R.id.idCurrentSalatNameView)?.setTextColor(value.secondaryColor)
            findViewById<TextClock>(R.id.idClockView)?.setTextColor(value.mainSeekBarProgressColor)
            findViewById<TextView>(R.id.idNextSalatTimeView)?.setTextColor(value.secondaryColor)
            findViewById<CircularSeekBar>(R.id.idProgressView)?.apply {
                circleProgressColor = value.mainSeekBarProgressColor
                pointerColor = value.mainSeekBarDotColor
                pointerHaloColor = value.mainSeekBarDotColor
                circleColor = value.mainSeekBarFillColor
            }

        }

    init {

        isClickable = true
        radius = 0f

        GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(theme.mainGradientStartColor, theme.mainGradientMiddleColor, theme.mainGradientEndColor)).apply {
            gradientRadius = -20f
        }

        addView(View(context).apply {
            id = R.id.idTopLineView
            background = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(theme.mainLineColor, theme.mainLineColor.getColorWithAlpha(0.2f)))
        }, LayoutParams(dp(2), dp(16)).apply {
            leftMargin = dp(19)
            topMargin = dp(16)
            bottomMargin = dp(16)
        })

        addView(View(context).apply {
            id = R.id.idMiddleLineView
            setBackgroundColor(theme.mainLineColor)
        }, LayoutParams(dp(2), full).apply {
            leftMargin = dp(19)
            topMargin = dp(32)
            bottomMargin = dp(32)
        })

        addView(View(context).apply {
            id = R.id.idBottomLineView
            background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(theme.mainLineColor, theme.mainLineColor.getColorWithAlpha(0.2f)))
        }, LayoutParams(dp(2), dp(16), Gravity.BOTTOM).apply {
            leftMargin = dp(19)
            topMargin = dp(16)
            bottomMargin = dp(16)
        })

        addView(FrameLayout(context).apply {

            setPadding(dp(8), dp(8), dp(8), dp(8))

            addView(CircularSeekBar(context).apply {
                id = R.id.idProgressView
                circleStrokeWidth = dp(4).toFloat()
                isEnabled = false
                max = 1f

                circleProgressColor = theme.mainSeekBarProgressColor
                pointerColor = theme.mainSeekBarDotColor
                circleColor = theme.mainSeekBarFillColor

            }, ViewGroup.LayoutParams(full, full))

            addView(LinearLayoutCompat(context).apply {

                orientation = LinearLayoutCompat.VERTICAL
                gravity = Gravity.CENTER

                addView(BaseTextView(context).apply {
                    id = R.id.idCurrentSalatNameView
                    gravity = Gravity.CENTER
                    setTextSizeSp(16)
                    setTypeface(typeface, Typeface.BOLD)
                }, LayoutParams(full, wrap))

                if (Build.VERSION.SDK_INT >= 17) {
                    addView(TextClock(context).apply {
                        id = R.id.idClockView
                        gravity = Gravity.CENTER
                        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                        format12Hour = null
                        setTextSizeSp(32)
                        setTextColor(theme.mainSeekBarProgressColor)
                    }, LayoutParams(full, wrap))
                } else {
                    addView(DigitalClock(context).apply {
                        id = R.id.idClockView
                        gravity = Gravity.CENTER
                        setTextSizeSp(32)
                        setTypeface(typeface, Typeface.BOLD)
                        setTextColor(theme.mainSeekBarProgressColor)
                    }, LayoutParams(full, wrap))
                }

                addView(BaseTextView(context).apply {
                    id = R.id.idNextSalatTimeView
                    gravity = Gravity.CENTER
                    setTextSizeSp(14)
                }, LayoutParams(full, wrap))

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

                        addView(BaseTextView(context).apply {
                            id = R.id.idGrigorianView
                            setTextSizeSp(14)
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            setTextColor(theme.secondaryColor)
                        }, LinearLayout.LayoutParams(full, wrap))

                        addView(BaseTextView(context).apply {
                            id = R.id.idHijriDate
                            alpha = 0.5f
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            setTextSizeSp(12)
                            setTextColor(theme.secondaryColor)
                        }, LinearLayout.LayoutParams(full, wrap))

                    }, LayoutParams(full, wrap).apply {
                        leftMargin = dp(28)
                    })

                }, LayoutParams(wrap, wrap, Gravity.BOTTOM))

            }, LinearLayout.LayoutParams(wrap, 0, 1f))

            addView(FrameLayout(context).apply {

                addView(AppCompatImageView(context).apply {
                    setImageResource(R.drawable.ic_circle_gradient)
                }, LayoutParams(dp(24), dp(24), Gravity.CENTER_VERTICAL))

                addView(CardView(context).apply {

                    id = R.id.idNextSalatLayout
                    setCardBackgroundColor(theme.mainIconsDarkColor)

                    addView(TrimmedTextView(context).apply {
                        id = R.id.idNextSalatView
                        gravity = Gravity.CENTER
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        setPadding(dp(16), dp(4), dp(16), dp(4))
                        setTypeface(typeface, Typeface.BOLD)
                        setTextSizeSp(14)
                        setTextColor(theme.secondaryColor)
                    }, LayoutParams(wrap, wrap, Gravity.CENTER))

                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                            radius = measuredHeight.toFloat() / 2
                        }
                    })

                }, LayoutParams(wrap, wrap, Gravity.CENTER_VERTICAL).apply {
                    leftMargin = dp(28)
                })

            }, LinearLayout.LayoutParams(full, 0, 1f))


            addView(LinearLayout(context).apply {

                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL

                addView(AppCompatImageView(context).apply {
                    setImageResource(R.drawable.ic_circle_gradient)
                }, LinearLayout.LayoutParams(dp(24), dp(24)))

                addView(BaseTextView(context).apply {
                    id = R.id.idNotificationView
                    text = string(R.string.notify)
                    gravity = Gravity.CENTER_VERTICAL
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                    setTextColor(theme.secondaryColor)
                }, LinearLayout.LayoutParams(wrap, wrap).apply {
                    leftMargin = dp(4)
                })

                addView(BaseImageButton(context).apply {
                    id = R.id.idNotificationImageView
                    setButtonPadding(dp(8))
                    setColorFilter(theme.mainSeekBarProgressColor)
                    supportBackgroundTintList = ColorStateList.valueOf(theme.mainIconsDarkColor)
                }, LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                    leftMargin = dp(8)
                })

            }, LinearLayout.LayoutParams(full, 0, 1f))

        }, LayoutParams(full, progressSize).apply {
            rightMargin = progressSize
        })

    }
}