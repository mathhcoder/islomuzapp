package uz.islom.ui.screens.pager

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import me.tankery.lib.circularseekbar.CircularSeekBar
import timber.log.Timber
import uz.islom.R
import uz.islom.android.colour
import uz.islom.android.drawable
import uz.islom.android.string
import uz.islom.io.subscribeKt
import uz.islom.model.app.FunctionType
import uz.islom.model.app.PrayTimeState
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
        return@lazy dp(168)
    }

    private val prayTimeViewModel by lazy {
        ViewModelProviders.of(this).get(SalatViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return NestedScrollView(inflater.context).apply {

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

                        background = (GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, intArrayOf(appTheme.mainGradientStartColor, appTheme.mainGradientEndColor)))

                        addView(FrameLayout(context).apply {

                            setPadding(dp(16), dp(16), dp(16), dp(16))

                            addView(CircularSeekBar(context).apply {
                                circleProgressColor = appTheme.mainSeekBarProgressColor
                                pointerColor = appTheme.mainSeekBarProgressColor
                                isEnabled = false
                                circleColor = appTheme.mainSeekBarColor

                            }, ViewGroup.LayoutParams(full, full))

                            addView(LinearLayoutCompat(context).apply {

                                orientation = LinearLayoutCompat.VERTICAL
                                gravity = Gravity.CENTER

                                addView(BaseTextView(context).apply {
                                    id = R.id.currentSalatView
                                    gravity = Gravity.CENTER
                                    setTextColor(appTheme.secondaryColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                                addView(TextClock(context).apply {
                                    gravity = Gravity.CENTER
                                    setTextSizeSp(24)
                                    setTextColor(appTheme.secondaryColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                                addView(BaseTextView(context).apply {
                                    id = R.id.nextSalatReservedTimeView
                                    gravity = Gravity.CENTER
                                    setTextColor(appTheme.secondaryColor)
                                }, FrameLayout.LayoutParams(full, wrap))

                            }, ViewGroup.LayoutParams(full, full))

                        }, FrameLayout.LayoutParams(progressSize, full, Gravity.END))

                        addView(View(context).apply {
                            setBackgroundColor(appTheme.mainLineColor)
                        }, FrameLayout.LayoutParams(dp(2), full).apply {
                            leftMargin = dp(19)
                            topMargin = dp(16)
                            bottomMargin = dp(16)
                        })

                        addView(LinearLayout(context).apply {

                            setPadding(dp(8), dp(16), 0, dp(16))
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL
                            weightSum = 3f

                            addView(LinearLayout(context).apply {

                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL

                                addView(AppCompatImageView(context).apply {
                                    setImageResource(R.drawable.ic_circle_gradient)
                                }, FrameLayout.LayoutParams(dp(24), dp(24)))

                                addView(BaseTextView(requireContext()).apply {
                                    setTextColor(appTheme.secondaryColor)
                                    text = "19 rajab, 1440"
                                })

                            }, LinearLayout.LayoutParams(full, 0, 1f))

                            addView(LinearLayout(context).apply {

                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL

                                addView(AppCompatImageView(context).apply {
                                    setImageResource(R.drawable.ic_circle_gradient)
                                }, ViewGroup.LayoutParams(dp(24), dp(24)))

                                addView(CardView(context).apply {
                                    setCardBackgroundColor(appTheme.mainIconsDarkColor)
                                    radius = dp(16).toFloat()
                                    addView(BaseTextView(context).apply {
                                        id = R.id.nextSalatView
                                        setPadding(dp(16), dp(8), dp(16), dp(8))
                                        setTextColor(appTheme.secondaryColor)
                                        gravity = Gravity.CENTER
                                    }, ViewGroup.LayoutParams(wrap, wrap))

                                }, ViewGroup.LayoutParams(wrap, wrap))

                            }, LinearLayout.LayoutParams(full, 0, 1f))


                            addView(LinearLayout(context).apply {

                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL

                                addView(AppCompatImageView(context).apply {
                                    setImageResource(R.drawable.ic_circle_gradient)
                                }, LinearLayout.LayoutParams(dp(24), dp(24)))


                                addView(BaseTextView(context).apply {
                                    text = "Eslatish"
                                    gravity = Gravity.CENTER_VERTICAL
                                    setTextColor(appTheme.secondaryColor)
                                }, LinearLayout.LayoutParams(wrap, wrap))

                                addView(BaseImageButton(context).apply {
                                    id = R.id.imageView
                                    setButtonPadding(dp(8))
                                }, LinearLayout.LayoutParams(dp(40), dp(40)))

                            }, LinearLayout.LayoutParams(full, 0, 1f))

                        }, FrameLayout.LayoutParams(full, full).apply {
                            rightMargin = progressSize
                        })

                    }, FrameLayout.LayoutParams(full, full))

                }, LinearLayout.LayoutParams(full, progressSize))

                addView(RecyclerView(requireContext()).apply {
                    layoutManager = GridLayoutManager(requireContext(), 3)
                    adapter = functionsAdapter
                    addItemDecoration(FunctionItemDecoration(dp(16), 3))
                    isNestedScrollingEnabled = true
                    overScrollMode = View.OVER_SCROLL_NEVER
                }, LinearLayout.LayoutParams(full, wrap).apply {
                    bottomMargin = dp(8)
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


        bindPrayTimeState(PrayTimeState.with(System.currentTimeMillis(), prayTimeViewModel.getCurrentSalatTimes()))

        UpdateCenter.subscribeTo(UpdatePath.SalatTimes())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    bindPrayTimeState(PrayTimeState.with(System.currentTimeMillis(), it))
                })

    }

    private fun bindPrayTimeState(prayTimeState: PrayTimeState) {
        view?.findViewById<BaseTextView>(R.id.currentSalatView)?.text = string(prayTimeState.currentSalatType.title)
        view?.findViewById<BaseTextView>(R.id.nextSalatView)?.text = string(prayTimeState.nextSalatType.title)
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