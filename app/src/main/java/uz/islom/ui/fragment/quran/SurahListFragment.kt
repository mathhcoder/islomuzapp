package uz.islom.ui.fragment.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.ext.recycler.ItemDivider
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.model.dm.Theme
import uz.islom.model.entity.Surah
import uz.islom.ui.cell.SurahCell
import uz.islom.ui.fragment.BaseFragment
import uz.islom.vm.SurahViewModel

class SurahListFragment : BaseFragment() {

    companion object {
        fun newInstance(theme: Theme) = SurahListFragment().apply {
            arguments = Bundle().apply {
                putSerializable("theme",theme)
            }
        }
    }

    private val appTheme by lazy {
        arguments?.getSerializable("theme") as Theme
    }

    private val surahViewModel by lazy {
        ViewModelProviders.of(this).get(SurahViewModel::class.java)
    }

    private val surahAdapter by lazy {
        SurahAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return FrameLayout(inflater.context).apply {

            addView(RecyclerView(context).apply {
                id = R.id.idRecyclerView
                layoutManager = LinearLayoutManager(context)
                overScrollMode = View.OVER_SCROLL_NEVER
                addItemDecoration(ItemDivider(context, appTheme, 0))
            }, FrameLayout.LayoutParams(full, full))

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.idRecyclerView).apply {
            adapter = surahAdapter
        }

        surahViewModel.data.observe(this, Observer {
            surahAdapter.data = it
        })

    }

    inner class SurahAdapter : RecyclerView.Adapter<SurahHolder>() {

        var data: List<Surah> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SurahHolder = SurahHolder(SurahCell(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(72))
        })

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: SurahHolder, p1: Int) {
            data.getOrNull(p1)?.let {
                p0.bindSurah(it)
            }

        }
    }

    inner class SurahHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindSurah(surah: Surah) {
            (itemView as? SurahCell)?.apply {
                order = surah.id
                nameArabic = surah.name?.ar
                nameLocal = surah.name?.uz
                //description = surahAdapter.description?.uz
//                setOnClickListener {
//                    (activity as? BaseActivity)?.navigationManager?.navigateToAsmaUlHusna(surahAdapter)
//                }
            }
        }
    }
}