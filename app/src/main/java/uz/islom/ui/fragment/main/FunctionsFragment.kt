package uz.islom.ui.fragment.main

import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import uz.islom.R
import uz.islom.ext.*
import uz.islom.ext.subscribeKt
import uz.islom.model.dm.DateState
import uz.islom.model.entity.Salat
import uz.islom.model.dm.SalatTimeState
import uz.islom.model.enums.FunctionType
import uz.islom.model.dm.Theme
import uz.islom.ui.BaseActivity
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ui.cell.FunctionCell
import uz.islom.ui.custom.FooterLayout
import uz.islom.ui.custom.SalatStateLayout
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

    private val stateSize by lazy {
        return@lazy context?.headerSize() ?: 0
    }

    private val prayTimeViewModel by lazy {
        ViewModelProviders.of(this).get(SalatViewModel::class.java)
    }

    private val salatStateLayout by lazy {
        view?.findViewById<SalatStateLayout>(R.id.idStateLayout)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val start = System.currentTimeMillis()
        Timber.i("SpeedManagement: FunctionsFragment onCreateView: start $start")

        val rootView = FrameLayout(inflater.context).apply {
            id = R.id.idRootLayout
            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(SalatStateLayout(context).apply {
                id = R.id.idStateLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, stateSize))

            addView(FooterLayout(context).apply {
                id = R.id.idFooterLayout
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(96), Gravity.BOTTOM))

            addView(RecyclerView(context).apply {
                id = R.id.idRecyclerView
                layoutManager = GridLayoutManager(context, 3)
                adapter = functionsAdapter
                addItemDecoration(FunctionItemDecoration(dp(16), 3))
                setPadding(dp(16), 0, dp(16), 0)
                isNestedScrollingEnabled = true
                overScrollMode = View.OVER_SCROLL_NEVER
            }, FrameLayout.LayoutParams(full, wrap).apply {
                topMargin = stateSize
            })
        }

        val end = System.currentTimeMillis()
        Timber.i("SpeedManagement: FunctionsFragment onCreateView: end $end")

        Timber.i("RenderManagement: FunctionsFragment view renderTime : ${end - start}")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("SpeedManagement: FunctionsFragment onViewCreated: start ${System.currentTimeMillis()}")

        view.findViewById<View>(R.id.idStateLayout)?.apply {
            setOnClickListener {
                (activity as? BaseActivity)?.navigationManager?.navigateToSalats()
            }
        }

        bindDate(DateState.with(Calendar.getInstance(), 1))
        bindSalats(prayTimeViewModel.getSalatTimes(Calendar.getInstance()))

        listenUpdates()
    }

    override fun onResume() {
        super.onResume()

        Timber.i("SpeedManagement: FunctionsFragment onResume: ${System.currentTimeMillis()}")

    }


    private fun listenUpdates() {
//
//        UpdateCenter.subscribeTo(UpdatePath.DateUpdate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeKt(Consumer {
//                    bindDate(DateState.with(it, 1))
//                })
//
//        UpdateCenter.subscribeTo(UpdatePath.SalatUpdate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeKt(Consumer {
//                    bindSalats(it)
//                })

    }


    override fun onThemeChanged(theme: Theme) {
        Timber.d("Theme changed $theme")
        view?.findViewById<View>(R.id.idRootLayout)?.setBackgroundColor(appTheme.mainListColor)
        functionsAdapter.theme = theme
        salatStateLayout?.theme = theme

    }

    private fun bindDate(dateState: DateState) {
        salatStateLayout?.grigorianDate = context?.calendar2uzbekTimeFormat(dateState.grigorian)
                ?: ""
        salatStateLayout?.hijriDate = context?.calendar2uzbekHijrTimeFormat(dateState.hijri) ?: ""
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

        salatStateLayout?.currentSalatName = string(salatTimeState.currentSalat.type.title) ?: ""
        salatStateLayout?.salatProgress = salatTimeState.progress
        salatStateLayout?.notificationImageResource = salatTimeState.nextSalat.notificationType.image
        salatStateLayout?.nextSalatName = SpannableStringBuilder().apply {
            append(string(salatTimeState.nextSalat.type.title)?.ellipsize())
            append(SimpleDateFormat(" HH:mm", Locale.getDefault()).format(salatTimeState.nextSalat.date.time))
        }.toString()

        string(R.string.left_salat_format)?.let {
            salatStateLayout?.remainingTime = String.format(it, salatTimeState.left.milliseconds2formattedTime())
        }

    }


    inner class FunctionsAdapter : RecyclerView.Adapter<FunctionHolder>() {

        var theme = Theme.GREEN
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
        fun bindFunction(function: FunctionType, theme: Theme) {
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
            val itemCount = parent.adapter?.itemCount ?: 0

            if (itemPosition < gridSize) {
                outRect.top = dp(16)
            } else {
                outRect.top = gridSpacing / 2
            }

            if (itemCount / gridSize - itemPosition / gridSize < 1) {
                outRect.bottom = dp(16)
            } else {
                outRect.bottom = gridSpacing / 2
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

        }
    }


}