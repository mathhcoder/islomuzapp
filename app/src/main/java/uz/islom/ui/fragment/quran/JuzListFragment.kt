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
import uz.islom.model.entity.Juz
import uz.islom.model.entity.Surah
import uz.islom.ui.BaseActivity
import uz.islom.ui.adapter.JuzListAdapter
import uz.islom.ui.cell.AsmaUlHusnaCell
import uz.islom.ui.cell.JuzCell
import uz.islom.ui.fragment.BaseFragment
import uz.islom.vm.JuzViewModel

class JuzListFragment : BaseFragment() {

    companion object {
        fun newInstance() = JuzListFragment()
    }

    private val juzViewModel by lazy {
        ViewModelProviders.of(this).get(JuzViewModel::class.java)
    }

    private val juzListAdapter by lazy {
        JuzListAdapter{
            (activity as? BaseActivity)?.navigationManager?.navigateToJuz(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        juzViewModel.loadMore(0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FrameLayout(inflater.context).apply {

            addView(RecyclerView(context).apply {
                id = R.id.idRecyclerView
                layoutManager = LinearLayoutManager(context)
                overScrollMode = View.OVER_SCROLL_NEVER
                addItemDecoration(ItemDivider(context, appTheme, 0))
                adapter = juzListAdapter
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

                    if (!juzViewModel.isFullyLoaded()
                            && !juzViewModel.isLoading
                            && (visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                            && lastVisibleItemPosition >= 0) {

                        juzViewModel.loadMore(juzListAdapter.itemCount)
                        juzViewModel.isLoading = true
                    }
                }
            })
        }

        juzViewModel.newItemsUpdate.let {
            it.value?.let { data ->
                submitData(true,data)
                juzViewModel.isLoading = false

            }
            it.observe(this, Observer { data ->
                submitData(false,data)
                juzViewModel.isLoading = false
            })
        }
    }

    private fun submitData(needClean: Boolean, dataResult: DataResult<Juz>) {
        if (dataResult.result) {
            juzListAdapter.submitItems(needClean, dataResult.data)
        } else {

        }
    }
}