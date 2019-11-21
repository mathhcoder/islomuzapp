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
import uz.islom.model.dm.Theme
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.ui.BaseActivity
import uz.islom.ui.cell.AsmaUlHusnaCell
import uz.islom.ui.cell.LoadingCell
import uz.islom.ui.custom.HeaderLayout
import uz.islom.ui.fragment.SwipeAbleFragment
import uz.islom.vm.AsmaUlHusnaViewModel


class AsmaUlHusnaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = AsmaUlHusnaFragment()
    }

    private val asmaUlHusnaAdapter = AsmaUlHusnaAdapter()
    private val asmaUlHusnaViewModel by lazy {
        ViewModelProviders.of(this).get(AsmaUlHusnaViewModel::class.java)
    }

    private var loading = false
    private val pageSize = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asmaUlHusnaViewModel.loadMore(pageSize, 0)
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
            adapter = asmaUlHusnaAdapter.apply {
                setItems(asmaUlHusnaViewModel.newItemsUpdate.value ?: ArrayList())
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val visibleItemCount = layoutManager?.childCount ?: 0
                    val totalItemCount = layoutManager?.itemCount ?: 0
                    val lastVisibleItemPosition = ((layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                            ?: 0)

                    if (!asmaUlHusnaViewModel.isFullyLoaded()
                            && !loading
                            && (visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                            && lastVisibleItemPosition >= 0) {

                        asmaUlHusnaViewModel.loadMore(pageSize, asmaUlHusnaAdapter.itemCount)
                        loading = true
                    }
                }
            })
        }

        asmaUlHusnaViewModel.newItemsUpdate.observe(this, Observer {
            asmaUlHusnaAdapter.addItems(it)
            asmaUlHusnaAdapter.isLoading(asmaUlHusnaViewModel.isFullyLoaded())
            loading = false
        })
    }

    inner class AsmaUlHusnaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var data = ArrayList<AsmaUlHusna>()
        private var isLoading = false

        fun setItems(items: List<AsmaUlHusna>) {
            data.clear()
            data.addAll(items)
            notifyDataSetChanged()
        }

        fun addItems(items: List<AsmaUlHusna>) {
            data.addAll(items)
            notifyDataSetChanged()
        }

        fun isLoading(isLoading: Boolean) {
            this.isLoading = isLoading
            if (isLoading) {
                data.add(AsmaUlHusna())
                notifyItemInserted(data.size - 1)
            } else {
                val position = data.size - 1
                val item = data.getOrNull(position)
                if (item != null) {
                    data.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
                if (p1 == 0) AsmaUlHusnaHolder(AsmaUlHusnaCell(p0.context).apply {
                    layoutParams = ViewGroup.LayoutParams(full, dp(72))
                }) else LoadingHolder(LoadingCell(p0.context).apply {
                    layoutParams = ViewGroup.LayoutParams(full, dp(72))
                })

        override fun getItemCount() = data.size

        override fun getItemViewType(position: Int): Int {
            return if (isLoading && data.size - 1 == position) 1 else 0
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            data.getOrNull(p1)?.let {
                (p0 as? AsmaUlHusnaHolder)?.bindOption(it)
            }
        }
    }

    inner class AsmaUlHusnaHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindOption(asmaUlHusna: AsmaUlHusna) {
            (itemView as? AsmaUlHusnaCell)?.apply {
                order = asmaUlHusna.id
                nameArabic = asmaUlHusna.name?.ar
                nameLocal = asmaUlHusna.name?.uz
                description = asmaUlHusna.description?.uz
                setOnClickListener {
                    (activity as? BaseActivity)?.navigationManager?.navigateToAsmaUlHusna(asmaUlHusna)
                }
            }
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view)
}