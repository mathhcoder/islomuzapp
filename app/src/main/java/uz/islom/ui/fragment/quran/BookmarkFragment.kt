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
import uz.islom.model.dm.DataResult
import uz.islom.model.dm.Theme
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Surah
import uz.islom.ui.BaseActivity
import uz.islom.ui.adapter.SurahListAdapter
import uz.islom.ui.cell.SurahCell
import uz.islom.ui.fragment.BaseFragment
import uz.islom.vm.SurahViewModel

class BookmarkFragment : BaseFragment() {

    companion object {
        fun newInstance() = BookmarkFragment()
    }

    private val surahViewModel by lazy {
        ViewModelProviders.of(this).get(SurahViewModel::class.java)
    }

    private val surahAdapter by lazy {
        SurahListAdapter{
            (activity as? BaseActivity)?.navigationManager?.navigateToSurah(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surahViewModel.loadMore(0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return FrameLayout(inflater.context).apply {

            addView(RecyclerView(context).apply {
                id = R.id.idRecyclerView
                layoutManager = LinearLayoutManager(context)
                overScrollMode = View.OVER_SCROLL_NEVER
                addItemDecoration(ItemDivider(context, appTheme, 0))
                adapter = surahAdapter
            }, FrameLayout.LayoutParams(full, full))

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.idRecyclerView).apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val visibleItemCount = layoutManager?.childCount ?: 0
                    val totalItemCount = layoutManager?.itemCount ?: 0
                    val lastVisibleItemPosition = ((layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                            ?: 0)

                    if (!surahViewModel.isFullyLoaded()
                            && !surahViewModel.isLoading
                            && (visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                            && lastVisibleItemPosition >= 0) {

                        surahViewModel.loadMore(surahAdapter.itemCount)
                        surahViewModel.isLoading = true
                    }
                }
            })
        }

        surahViewModel.newItemsUpdate.let {
            it.value?.let { data ->
                submitData(true,data)
                surahViewModel.isLoading = false

            }
            it.observe(this, Observer { data ->
                submitData(false,data)
                surahViewModel.isLoading = false
            })
        }
    }

    private fun submitData(needClean: Boolean, dataResult: DataResult<Surah>) {
        if (dataResult.result) {
            surahAdapter.submitItems(needClean, dataResult.data)
        } else {

        }
    }
}