package uz.islom.ui.fragment.asma

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
import uz.islom.ext.string
import uz.islom.model.dm.DataResult
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.ui.BaseActivity
import uz.islom.ui.adapter.AsmaUlHusnaListAdapter
import uz.islom.ui.custom.HeaderLayout
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.vm.AsmaUlHusnaViewModel


class AsmaUlHusnaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = AsmaUlHusnaFragment()
    }

    private val asmaUlHusnaAdapter by lazy {
        AsmaUlHusnaListAdapter {
            (activity as? BaseActivity)?.navigationManager?.navigateToAsmaUlHusna(it)
        }
    }
    private val asmaUlHusnaViewModel by lazy {
        ViewModelProviders.of(this).get(AsmaUlHusnaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asmaUlHusnaViewModel.loadMore(0)
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return FrameLayout(inflater.context).apply {

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                title = string(R.string.asma_ul_husna)
                theme = appTheme
                setUpBackAction {
                    fragmentManager?.popBackStack()
                }
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(FrameLayout(context).apply {

                addView(RecyclerView(context).apply {
                    id = R.id.idRecyclerView
                    layoutManager = LinearLayoutManager(context)
                    overScrollMode = View.OVER_SCROLL_NEVER
                    addItemDecoration(ItemDivider(context, appTheme, 0))
                    adapter = asmaUlHusnaAdapter
                }, FrameLayout.LayoutParams(full, full))

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })

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

                    if (!asmaUlHusnaViewModel.isFullyLoaded()
                            && !asmaUlHusnaViewModel.isLoading
                            && (visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                            && lastVisibleItemPosition >= 0) {

                        asmaUlHusnaViewModel.loadMore(asmaUlHusnaAdapter.itemCount)
                        asmaUlHusnaViewModel.isLoading = true
                    }
                }
            })
        }

        asmaUlHusnaViewModel.newItemsUpdate.let {
            it.value?.let { data ->
                submitData(true,data)
                asmaUlHusnaViewModel.isLoading = false

            }
            it.observe(this, Observer { data ->
                submitData(false,data)
                asmaUlHusnaViewModel.isLoading = false
            })
        }
    }

    private fun submitData(needClean: Boolean, dataResult: DataResult<AsmaUlHusna>) {
        if (dataResult.result) {
            asmaUlHusnaAdapter.submitItems(needClean, dataResult.data)
        } else {

        }
    }

}