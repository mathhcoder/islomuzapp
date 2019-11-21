package uz.islom.ui.fragment.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import timber.log.Timber
import uz.islom.R
import uz.islom.ext.*
import uz.islom.model.dm.Salat
import uz.islom.model.dm.Theme
import uz.islom.model.enums.SalatType
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.ui.cell.SalatCell
import uz.islom.ui.custom.HeaderLayout
import uz.islom.vm.SalatViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SalatFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = SalatFragment()
    }

    private val salatsAdapter = SalatsAdapter()

    private val salatViewModel by lazy {
        ViewModelProviders.of(this).get(SalatViewModel::class.java)
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return NestedScrollView(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(LinearLayout(context).apply {

                orientation = LinearLayout.VERTICAL

                addView(HeaderLayout(context).apply {
                    id = R.id.idHeaderLayout
                    title = string(R.string.quran)
                    theme = appTheme
                    setUpBackAction {
                        fragmentManager?.popBackStack()
                    }
                    setUpFirstAction(R.drawable.ic_settings_material){

                    }
                }, FrameLayout.LayoutParams(full, dp(56)))

                addView(TabLayout(context).apply {
                    id = R.id.idTabLayout
                    setBackgroundColor(appTheme.toolBarColor)
                    setTabTextColors(appTheme.secondaryColor.getColorWithAlpha(0.7f), appTheme.secondaryColor)
                    setSelectedTabIndicatorColor(appTheme.secondaryColor)

                    addTab(newTab().apply {
                        setText(R.string.surah)
                    })
                    addTab(newTab().apply {
                        setText(R.string.juz)
                    })
                    addTab(newTab().apply {
                        setText(R.string.bookmark)
                    })

                    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            view?.findViewById<ViewPager>(R.id.idViewPager)?.currentItem = selectedTabPosition
                        }

                    })

                }, FrameLayout.LayoutParams(full, dp(56)))

                addView(CalendarView(context).apply {
                    id = R.id.calendarView
                }, LinearLayout.LayoutParams(full, wrap))

                addView(RecyclerView(context).apply {
                    id = R.id.idRecyclerView
                    layoutManager = LinearLayoutManager(context).apply {
                        isItemPrefetchEnabled = false
                    }
                    overScrollMode = View.OVER_SCROLL_NEVER
                    isNestedScrollingEnabled = true

                }, FrameLayout.LayoutParams(full, full).apply {
                    topMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, full))

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<CalendarView>(R.id.calendarView).apply {
            setOnDateChangeListener { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.MONTH, month)
                    set(Calendar.YEAR, year)
                }
                salatsAdapter.setSalats(salatViewModel.getSalatTimes(date).filter {
                    it.type == SalatType.FAJR ||
                            it.type == SalatType.DHUHR ||
                            it.type == SalatType.ASR ||
                            it.type == SalatType.MAGHRIB ||
                            it.type == SalatType.ISHA
                })
            }
        }

        view.findViewById<RecyclerView>(R.id.idRecyclerView).apply {
            adapter = salatsAdapter
        }

        salatsAdapter.setSalats(salatViewModel.getSalatTimes(Calendar.getInstance()).filter {
            it.type == SalatType.FAJR ||
                    it.type == SalatType.DHUHR ||
                    it.type == SalatType.ASR ||
                    it.type == SalatType.MAGHRIB ||
                    it.type == SalatType.ISHA
        })
    }

    inner class SalatsAdapter : RecyclerView.Adapter<SalatHolder>() {

        private val data = ArrayList<Salat>()

        fun setSalats(salats: List<Salat>) {
            Timber.d("Salats : $salats")
            data.clear()
            data.addAll(salats)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalatHolder = SalatHolder(SalatCell(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(56))
        })

        override fun onViewAttachedToWindow(holder: SalatHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: SalatHolder, p1: Int) {
            p0.bindSalat(data[p1])
        }
    }

    inner class SalatHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindSalat(salat: Salat) {
            (itemView as? SalatCell)?.notificationTypeIcon = drawable(salat.notificationType.image)
            (itemView as? SalatCell)?.salatTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time)
            (itemView as? SalatCell)?.salatName = string(salat.type.title) ?: ""
        }
    }
}