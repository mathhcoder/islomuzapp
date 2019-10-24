package uz.islom.ui.screens.pager

import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.tankery.lib.circularseekbar.CircularSeekBar
import uz.islom.BuildConfig
import uz.islom.R
import uz.islom.android.colour
import uz.islom.android.drawable
import uz.islom.android.screenWidth
import uz.islom.android.string
import uz.islom.ext.*
import uz.islom.io.subscribeKt
import uz.islom.model.app.DateState
import uz.islom.model.app.Salat
import uz.islom.model.app.SalatTimeState
import uz.islom.model.enums.FunctionType
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.base.BaseFragment
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.cells.FunctionCell
import uz.islom.ui.custom.TrimmedTextView
import uz.islom.ui.util.*
import uz.islom.update.UpdateCenter
import uz.islom.update.UpdatePath
import uz.islom.vm.SalatViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class FunctionsFragment : BaseFragment() {

    companion object {
        fun newInstance() = FunctionsFragment().apply {}
    }

    private val functionsAdapter by lazy {
        return@lazy FunctionsAdapter().apply {
            data = FunctionType.values().toMutableList()
        }
    }

    private val progressSize by lazy {
        return@lazy (context?.screenWidth() ?: 0) / 2 - dp(16)
    }

    private val prayTimeViewModel by lazy {
        ViewModelProviders.of(this).get(SalatViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return NestedScrollView(inflater.context).apply {

            id = R.id.idRootLayout

            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(LinearLayout(context).apply {

                descendantFocusability = LinearLayout.FOCUS_BLOCK_DESCENDANTS

                orientation = LinearLayout.VERTICAL

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER
                    maxLines = 1
                    text = string(R.string.app_name)
                    setTextColor(AppTheme.GREEN.secondaryColor)
                    setBackgroundColor(AppTheme.GREEN.toolBarColor)
                    setTextSizeSp(20)
                }, LinearLayout.LayoutParams(full, dp(56)))

                addView(MaterialCardView(context).apply {

                    id = R.id.idSalatsClickableLayout
                    isClickable = true

                    addView(FrameLayout(context).apply {

                        id = R.id.idSalatInfoLayout
                        radius = 0f

                        addView(View(context).apply {
                            id = R.id.idTopLineView
                        }, FrameLayout.LayoutParams(dp(2), dp(16)).apply {
                            leftMargin = dp(19)
                            topMargin = dp(16)
                            bottomMargin = dp(16)
                        })

                        addView(View(context).apply {
                            id = R.id.idMiddleLineView
                        }, FrameLayout.LayoutParams(dp(2), full).apply {
                            leftMargin = dp(19)
                            topMargin = dp(32)
                            bottomMargin = dp(32)
                        })

                        addView(View(context).apply {
                            id = R.id.idBottomLineView
                        }, FrameLayout.LayoutParams(dp(2), dp(16), Gravity.BOTTOM).apply {
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

                            }, ViewGroup.LayoutParams(full, full))

                            addView(LinearLayoutCompat(context).apply {

                                orientation = LinearLayoutCompat.VERTICAL
                                gravity = Gravity.CENTER

                                addView(BaseTextView(context).apply {
                                    id = R.id.idCurrentSalatNameView
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(16)
                                    setTypeface(typeface, Typeface.BOLD)
                                }, FrameLayout.LayoutParams(full, wrap))

                                if(Build.VERSION.SDK_INT>=17) {
                                    addView(TextClock(context).apply {
                                        id = R.id.idClockView
                                        gravity = Gravity.CENTER
                                        setTextSizeSp(32)
                                        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                                        format12Hour = null
                                    }, FrameLayout.LayoutParams(full, wrap))
                                }else{
                                    addView(DigitalClock(context).apply {
                                        id = R.id.idClockView
                                        gravity = Gravity.CENTER
                                        setTextSizeSp(32)
                                        setTypeface(typeface, Typeface.BOLD)
                                    }, FrameLayout.LayoutParams(full, wrap))
                                }

                                addView(BaseTextView(context).apply {
                                    id = R.id.idNextSalatTimeView
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(14)
                                }, FrameLayout.LayoutParams(full, wrap))

                            }, ViewGroup.LayoutParams(full, full))

                        }, FrameLayout.LayoutParams(progressSize, full, Gravity.END))

                        addView(LinearLayout(context).apply {

                            setPadding(dp(8), dp(16), 0, dp(16))
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL
                            weightSum = 3f

                            addView(FrameLayout(context).apply {

                                addView(FrameLayout(context).apply {

                                    addView(AppCompatImageView(context).apply {
                                        setImageResource(R.drawable.ic_circle_gradient)
                                    }, FrameLayout.LayoutParams(dp(24), dp(24)))

                                    addView(LinearLayout(context).apply {

                                        orientation = LinearLayout.VERTICAL

                                        addView(BaseTextView(context).apply {
                                            id = R.id.idGrigorianView
                                            setTextSizeSp(14)
                                            maxLines = 1
                                            ellipsize = TextUtils.TruncateAt.END
                                        }, LinearLayout.LayoutParams(full, wrap))

                                        addView(BaseTextView(context).apply {
                                            id = R.id.idHijriDate
                                            alpha = 0.5f
                                            maxLines = 1
                                            ellipsize = TextUtils.TruncateAt.END
                                            setTextSizeSp(12)
                                        }, LinearLayout.LayoutParams(full, wrap))

                                    }, FrameLayout.LayoutParams(full, wrap).apply {
                                        leftMargin = dp(28)
                                    })

                                }, FrameLayout.LayoutParams(wrap, wrap, Gravity.BOTTOM))

                            }, LinearLayout.LayoutParams(wrap, 0, 1f))

                            addView(FrameLayout(context).apply {

                                addView(AppCompatImageView(context).apply {
                                    setImageResource(R.drawable.ic_circle_gradient)
                                }, FrameLayout.LayoutParams(dp(24), dp(24), Gravity.CENTER_VERTICAL))

                                addView(CardView(context).apply {

                                    id = R.id.idNextSalatLayout

                                    addView(TrimmedTextView(context).apply {
                                        id = R.id.idNextSalatView
                                        setPadding(dp(16), dp(4), dp(16), dp(4))
                                        setTypeface(typeface, Typeface.BOLD)
                                        maxLines = 1
                                        ellipsize = TextUtils.TruncateAt.END
                                        setTextSizeSp(14)
                                        gravity = Gravity.CENTER
                                    }, FrameLayout.LayoutParams(wrap, wrap, Gravity.CENTER))

                                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                                        override fun onGlobalLayout() {
                                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                                            radius = measuredHeight.toFloat() / 2
                                        }
                                    })

                                }, FrameLayout.LayoutParams(wrap, wrap, Gravity.CENTER_VERTICAL).apply {
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
                                }, LinearLayout.LayoutParams(wrap, wrap).apply {
                                    leftMargin = dp(4)
                                })

                                addView(BaseImageButton(context).apply {
                                    id = R.id.idNotificationImageView
                                    setButtonPadding(dp(8))
                                }, LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                                    leftMargin = dp(8)
                                })

                            }, LinearLayout.LayoutParams(full, 0, 1f))

                        }, FrameLayout.LayoutParams(full, full).apply {
                            rightMargin = progressSize
                        })

                    }, FrameLayout.LayoutParams(full, full))

                }, LinearLayout.LayoutParams(full, progressSize))

                addView(FrameLayout(context).apply {

                    addView(AppCompatImageView(context).apply {
                        id = R.id.idFooterView
                        scaleType = ImageView.ScaleType.FIT_XY

                    }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))

                    addView(RecyclerView(context).apply {
                        id = R.id.recyclerView
                        layoutManager = GridLayoutManager(context, 3)
                        adapter = functionsAdapter
                        addItemDecoration(FunctionItemDecoration(dp(16), 3))
                        setPadding(dp(16), 0, dp(16), 0)
                        isNestedScrollingEnabled = true
                        overScrollMode = View.OVER_SCROLL_NEVER
                    }, LinearLayout.LayoutParams(full, wrap).apply {
                        bottomMargin = dp(16)
                        topMargin = dp(16)
                    })

                }, FrameLayout.LayoutParams(full, wrap))

            }, ViewGroup.LayoutParams(full, wrap))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.idSalatsClickableLayout)?.apply {
            setOnClickListener {
                (activity as? BaseActivity)?.navigationManager?.navigateToSalats()
            }
        }

        view.findViewById<ImageButton>(R.id.idNotificationImageView)?.apply {
            setBackgroundResource(R.drawable.ic_circle)
            setOnClickListener {

            }
        }

        view.findViewById<ImageView>(R.id.idFooterView)?.apply {
            setImageResource(R.drawable.img_footer)
        }

        bindTheme(AppTheme.GREEN)
        bindDate(DateState.with(Calendar.getInstance(), 1))
        bindSalats(prayTimeViewModel.getSalatTimes(Calendar.getInstance()))

        listenUpdates()
    }


    private fun listenUpdates() {

        UpdateCenter.subscribeTo(UpdatePath.DateUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindDate(DateState.with(it, 1))
                })

        UpdateCenter.subscribeTo(UpdatePath.SalatUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindSalats(it)
                })

        UpdateCenter.subscribeTo(UpdatePath.ThemeUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindTheme(it)
                })
    }

    private fun bindTheme(appTheme: AppTheme) {
        view?.findViewById<View>(R.id.idRootLayout)?.setBackgroundColor(appTheme.mainListColor)
        view?.findViewById<View>(R.id.idSalatInfoLayout)?.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(appTheme.mainGradientStartColor, appTheme.mainGradientMiddleColor, appTheme.mainGradientEndColor)).apply {
            gradientRadius = -20f
        }

        view?.findViewById<View>(R.id.idTopLineView)?.background = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(appTheme.mainLineColor, appTheme.mainLineColor.getColorWithAlpha(0.2f)))
        view?.findViewById<View>(R.id.idMiddleLineView)?.setBackgroundColor(appTheme.mainLineColor)
        view?.findViewById<View>(R.id.idBottomLineView)?.background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(appTheme.mainLineColor, appTheme.mainLineColor.getColorWithAlpha(0.2f)))

        view?.findViewById<BaseTextView>(R.id.idGrigorianView)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<BaseTextView>(R.id.idHijriDate)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<CardView>(R.id.idNextSalatLayout)?.setCardBackgroundColor(appTheme.mainIconsDarkColor)
        view?.findViewById<TextView>(R.id.idNextSalatView)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<TextView>(R.id.idNotificationView)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<BaseImageButton>(R.id.idNotificationImageView)?.setColorFilter(appTheme.mainSeekBarProgressColor)
        view?.findViewById<BaseImageButton>(R.id.idNotificationImageView)?.supportBackgroundTintList = ColorStateList.valueOf(appTheme.mainIconsDarkColor)

        view?.findViewById<TextView>(R.id.idCurrentSalatNameView)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<TextClock>(R.id.idClockView)?.setTextColor(appTheme.mainSeekBarProgressColor)
        view?.findViewById<TextView>(R.id.idNextSalatTimeView)?.setTextColor(appTheme.secondaryColor)
        view?.findViewById<CircularSeekBar>(R.id.idProgressView)?.apply {
            circleProgressColor = appTheme.mainSeekBarProgressColor
            pointerColor = appTheme.mainSeekBarDotColor
            circleColor = appTheme.mainSeekBarFillColor
        }

        functionsAdapter.theme = appTheme

    }

    private fun bindDate(dateState: DateState) {
        view?.findViewById<TextView>(R.id.idGrigorianView)?.text = view?.context?.calendar2uzbekTimeFormat(dateState.grigorian)
        view?.findViewById<TextView>(R.id.idHijriDate)?.text = view?.context?.calendar2uzbekHijrTimeFormat(dateState.hijri)
    }

    private fun bindSalats(salats: ArrayList<Salat>) {

        Single.fromCallable { SalatTimeState.with(salats) }
                .subscribeOn(Schedulers.computation())
                .delay(1000, TimeUnit.MILLISECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindSalatTimeState(it)
                })

    }


    private fun bindSalatTimeState(salatTimeState: SalatTimeState) {

        view?.findViewById<BaseTextView>(R.id.idCurrentSalatNameView)?.text = string(salatTimeState.currentSalat.type.title)
        view?.findViewById<CircularSeekBar>(R.id.idProgressView)?.progress = salatTimeState.progress
        view?.findViewById<ImageView>(R.id.idNotificationImageView)?.setImageResource(salatTimeState.nextSalat.notificationType.image)
        view?.findViewById<BaseTextView>(R.id.idNextSalatView)?.text = SpannableStringBuilder().apply {
            append(string(salatTimeState.nextSalat.type.title)?.ellipsize())
            append(SimpleDateFormat(" HH:mm", Locale.getDefault()).format(salatTimeState.nextSalat.date.time))
        }

        string(R.string.left_salat_format)?.let {
            view?.findViewById<BaseTextView>(R.id.idNextSalatTimeView)?.text = String.format(it, salatTimeState.left.milliseconds2formattedTime())
        }

    }


    inner class FunctionsAdapter : RecyclerView.Adapter<FunctionHolder>() {

        var theme = AppTheme.DARK
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        var data: List<FunctionType> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FunctionHolder = FunctionHolder(FunctionCell(p0.context))

        override fun onViewAttachedToWindow(holder: FunctionHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {
                data.getOrNull(holder.adapterPosition)?.let {
                    (activity as? BaseActivity)?.navigationManager?.navigateToFunction(it)
                }
            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: FunctionHolder, p1: Int) {
            p0.bindFunction(data[p1], theme)
        }
    }

    inner class FunctionHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFunction(function: FunctionType, theme: AppTheme) {
            (itemView as? FunctionCell)?.imageRes = function.imageRes
            (itemView as? FunctionCell)?.textRes = function.nameRes
            (itemView as? FunctionCell)?.theme = theme

        }
    }

    inner class FunctionItemDecoration(private val gridSpacing: Int, private val gridSize: Int) :
            RecyclerView.ItemDecoration() {

        private var needLeftSpacing = false

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val frameWidth = ((parent.width - parent.paddingLeft - parent.paddingRight - gridSpacing.toFloat() * (gridSize - 1)) / gridSize).toInt()
            val padding = (parent.width - parent.paddingLeft - parent.paddingRight) / gridSize - frameWidth
            val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

            if (itemPosition < gridSize) {
                outRect.top = gridSpacing
            } else {
                outRect.top = gridSpacing / 2
            }

            if (itemPosition % gridSize == 0) {
                outRect.left = dp(2)
                outRect.right = padding
                needLeftSpacing = true
            } else if ((itemPosition + 1) % gridSize == 0) {
                needLeftSpacing = false
                outRect.right = dp(2)
                outRect.left = padding
            } else if (needLeftSpacing) {
                needLeftSpacing = false
                outRect.left = gridSpacing - padding
                if ((itemPosition + 2) % gridSize == 0) {
                    outRect.right = gridSpacing - padding
                } else {
                    outRect.right = gridSpacing / 2
                }
            } else if ((itemPosition + 2) % gridSize == 0) {
                needLeftSpacing = false
                outRect.left = gridSpacing / 2
                outRect.right = gridSpacing - padding
            } else {
                needLeftSpacing = false
                outRect.left = gridSpacing / 2
                outRect.right = gridSpacing / 2
            }

            outRect.bottom = gridSpacing / 2

        }
    }


}