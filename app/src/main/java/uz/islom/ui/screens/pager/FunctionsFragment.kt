package uz.islom.ui.screens.pager

import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextClock
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
import uz.islom.R
import uz.islom.android.colour
import uz.islom.android.drawable
import uz.islom.android.screenWidth
import uz.islom.android.string
import uz.islom.ext.calendar2uzbekHijrTimeFormat
import uz.islom.ext.calendar2uzbekTimeFormat
import uz.islom.ext.getColorWithAlpha
import uz.islom.ext.milliseconds2formattedTime
import uz.islom.io.subscribeKt
import uz.islom.model.app.HijriDateState
import uz.islom.model.app.SalatTimeState
import uz.islom.model.enums.FunctionType
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.base.BaseFragment
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.cells.FunctionCell
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

    private val appTheme by lazy {
        return@lazy AppTheme.GREEN
    }

    private val functionsAdapter by lazy {
        return@lazy FunctionsAdapter().apply {
            data = FunctionType.values().toMutableList()
        }
    }

    private val progressSize by lazy {
        return@lazy (context?.screenWidth() ?: 0) / 2
    }

    private val prayTimeViewModel by lazy {
        ViewModelProviders.of(this).get(SalatViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return NestedScrollView(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

            setBackgroundColor(appTheme.mainListColor)

            addView(LinearLayout(context).apply {

                descendantFocusability = LinearLayout.FOCUS_BLOCK_DESCENDANTS

                orientation = LinearLayout.VERTICAL

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER
                    maxLines = 1
                    text = string(R.string.app_name)
                    setTextColor(appTheme.secondaryColor)
                    setBackgroundColor(appTheme.toolBarColor)
                    setTextSizeSp(20)
                }, LinearLayout.LayoutParams(full, dp(56)))

                addView(MaterialCardView(context).apply {

                    id = R.id.salatsView

                    isClickable = true

                    addView(FrameLayout(context).apply {

                        background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(appTheme.mainGradientStartColor, appTheme.mainGradientMiddleColor, appTheme.mainGradientEndColor)).apply {
                            gradientRadius = -20f
                        }

                        radius = 0f

                        addView(FrameLayout(context).apply {

                            setPadding(dp(16), dp(16), dp(16), dp(16))

                            addView(CircularSeekBar(context).apply {
                                id = R.id.progressView
                                circleProgressColor = appTheme.mainSeekBarProgressColor
                                pointerColor = appTheme.mainSeekBarProgressColor
                                circleStrokeWidth = dp(4).toFloat()
                                isEnabled = false
                                circleColor = appTheme.mainSeekBarColor
                                max = 1f

                            }, ViewGroup.LayoutParams(full, full))

                            addView(LinearLayoutCompat(context).apply {

                                orientation = LinearLayoutCompat.VERTICAL
                                gravity = Gravity.CENTER

                                addView(BaseTextView(context).apply {
                                    id = R.id.currentSalatView
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(16)
                                    setTypeface(typeface, Typeface.BOLD)
                                    setTextColor(appTheme.secondaryColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                                addView(TextClock(context).apply {
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(32)
                                    setTypeface(typeface, Typeface.BOLD)
                                    setTextColor(appTheme.mainSeekBarProgressColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                                addView(BaseTextView(context).apply {
                                    id = R.id.nextSalatReservedTimeView
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(14)
                                    setTextColor(appTheme.secondaryColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                            }, ViewGroup.LayoutParams(full, full))

                        }, FrameLayout.LayoutParams(progressSize, full, Gravity.END))

                        addView(View(context).apply {
                            background = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(appTheme.mainLineColor, appTheme.mainLineColor.getColorWithAlpha(0.2f)))
                        }, FrameLayout.LayoutParams(dp(2), dp(16)).apply {
                            leftMargin = dp(19)
                            topMargin = dp(16)
                            bottomMargin = dp(16)
                        })

                        addView(View(context).apply {
                            setBackgroundColor(appTheme.mainLineColor)
                        }, FrameLayout.LayoutParams(dp(2), full).apply {
                            leftMargin = dp(19)
                            topMargin = dp(32)
                            bottomMargin = dp(32)
                        })

                        addView(View(context).apply {
                            background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(appTheme.mainLineColor, appTheme.mainLineColor.getColorWithAlpha(0.2f)))
                        }, FrameLayout.LayoutParams(dp(2), dp(16), Gravity.BOTTOM).apply {
                            leftMargin = dp(19)
                            topMargin = dp(16)
                            bottomMargin = dp(16)
                        })

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
                                            id = R.id.grigorianDateView
                                            setTextColor(appTheme.secondaryColor)
                                            setTextSizeSp(14)
                                        }, LinearLayout.LayoutParams(full, wrap))

                                        addView(BaseTextView(context).apply {
                                            id = R.id.hijriDateView
                                            setTextColor(appTheme.secondaryColor)
                                            alpha = 0.5f
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

                                    radius = dp(20).toFloat()
                                    setCardBackgroundColor(appTheme.mainIconsDarkColor)

                                    addView(BaseTextView(context).apply {
                                        id = R.id.nextSalatNameView
                                        setPadding(dp(16), dp(4), dp(16), dp(4))
                                        setTextColor(appTheme.secondaryColor)
                                        setTypeface(typeface, Typeface.BOLD)
                                        setTextSizeSp(16)
                                        gravity = Gravity.CENTER
                                    }, FrameLayout.LayoutParams(wrap, wrap, Gravity.CENTER))

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
                                    text = string(R.string.notify)
                                    gravity = Gravity.CENTER_VERTICAL
                                    setTextColor(appTheme.secondaryColor)
                                }, LinearLayout.LayoutParams(wrap, wrap).apply {
                                    leftMargin = dp(4)
                                })

                                addView(BaseImageButton(context).apply {
                                    id = R.id.imageView
                                    setButtonPadding(dp(8))
                                    setColorFilter(appTheme.mainSeekBarProgressColor)
                                }, LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                                    leftMargin = dp(8)
                                })

                            }, LinearLayout.LayoutParams(full, 0, 1f))

                        }, FrameLayout.LayoutParams(full, full).apply {
                            rightMargin = progressSize
                        })

                    }, FrameLayout.LayoutParams(full, full))

                }, LinearLayout.LayoutParams(full, progressSize))

                addView(RecyclerView(context).apply {
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

            }, ViewGroup.LayoutParams(full, wrap))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<BaseImageButton>(R.id.imageView)?.apply {
            setImageResources(R.drawable.ic_volume_high, colour(R.color.white))
            background = drawable(R.drawable.ic_circle, appTheme.mainIconsDarkColor)
        }

        view.findViewById<View>(R.id.salatsView)?.apply {
            setOnClickListener {
                (activity as? BaseActivity)?.navigationManager?.navigateToSalats()
            }
        }


        bindDate(HijriDateState.with(Calendar.getInstance(), 1))
        bindSalats()

        UpdateCenter.subscribeTo(UpdatePath.DateChanged())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindDate(HijriDateState.with(it, 1))
                })


    }

    private fun bindSalats() {

        val salatTimes = prayTimeViewModel.getSalatTimes(Calendar.getInstance())

        Single.fromCallable { SalatTimeState.with(salatTimes) }
                .subscribeOn(Schedulers.computation())
                .delay(1000, TimeUnit.MILLISECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindSalatTimeState(it)
                })

    }


    private fun bindSalatTimeState(salatTimeState: SalatTimeState) {

        view?.findViewById<BaseTextView>(R.id.currentSalatView)?.text = string(salatTimeState.currentSalat.type.title)
        view?.findViewById<CircularSeekBar>(R.id.progressView)?.progress = salatTimeState.progress
        view?.findViewById<ImageView>(R.id.imageView)?.setImageResource(salatTimeState.nextSalat.notificationType.image)
        string(R.string.next_salat_format)?.let {
            view?.findViewById<BaseTextView>(R.id.nextSalatNameView)?.text = String.format(it, string(salatTimeState.nextSalat.type.title), SimpleDateFormat("HH:mm", Locale.getDefault()).format(salatTimeState.nextSalat.date.time))
        }
        string(R.string.left_salat_format)?.let {
            view?.findViewById<BaseTextView>(R.id.nextSalatReservedTimeView)?.text = String.format(it, salatTimeState.left.milliseconds2formattedTime())
        }

    }

    private fun bindDate(dateState: HijriDateState) {
        view?.findViewById<BaseTextView>(R.id.grigorianDateView)?.text = view?.context?.calendar2uzbekTimeFormat(dateState.grigorian)
        view?.findViewById<BaseTextView>(R.id.hijriDateView)?.text = view?.context?.calendar2uzbekHijrTimeFormat(dateState.hijri)
    }

    inner class FunctionsAdapter : RecyclerView.Adapter<FunctionHolder>() {

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
            p0.bindFunction(data[p1])
        }
    }

    inner class FunctionHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFunction(function: FunctionType) {
            (itemView as? FunctionCell)?.imageRes = function.imageRes
            (itemView as? FunctionCell)?.textRes = function.nameRes
        }
    }

    inner class FunctionItemDecoration(private val gridSpacing: Int, private val gridSize: Int) :
            RecyclerView.ItemDecoration() {

        private var needLeftSpacing = false

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val frameWidth = ((parent.width - gridSpacing.toFloat() * (gridSize - 1)) / gridSize).toInt()
            val padding = parent.width / gridSize - frameWidth
            val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

            if (itemPosition < gridSize) {
                outRect.top = gridSpacing
            } else {
                outRect.top = gridSpacing / 2
            }

            if (itemPosition % gridSize == 0) {
                outRect.left = gridSpacing
                outRect.right = padding
                needLeftSpacing = true
            } else if ((itemPosition + 1) % gridSize == 0) {
                needLeftSpacing = false
                outRect.right = gridSpacing
                outRect.left = padding
            } else if (needLeftSpacing) {
                needLeftSpacing = false
                outRect.left = gridSpacing - padding
                if ((itemPosition + 2) % gridSize == 0) {
                    outRect.right = gridSpacing - padding
                } else {
                    outRect.right = gridSpacing / 2
                }
//            } else if ((itemPosition + 2) % gridSize == 0) {
//                needLeftSpacing = false
//                outRect.left = gridSpacing / 2
//                outRect.right = gridSpacing - padding
//            } else {
                needLeftSpacing = false
                outRect.left = gridSpacing / 2
                outRect.right = gridSpacing / 2
            }

            outRect.bottom = gridSpacing / 2

        }
    }


}