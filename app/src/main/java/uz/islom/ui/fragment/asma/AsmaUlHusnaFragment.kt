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
import uz.islom.ext.*
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.enums.ThemeType
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.ui.cell.AsmaUlHusnaCell
import uz.islom.ui.custom.HeaderLayout
import uz.islom.vm.AsmaUlHusnaViewModel

class AsmaUlHusnaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = AsmaUlHusnaFragment()
    }

    private val asmaUlHusnaAdapter = AsmaUlHusnaAdapter()
    private val asmaUlHusnaViewModel by lazy {
        ViewModelProviders.of(this).get(AsmaUlHusnaViewModel::class.java)
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: ThemeType): View? {
        return FrameLayout(inflater.context).apply {

            addView(HeaderLayout(context).apply {
                id = R.id.idHeaderLayout
                title = string(R.string.asma_ul_husna)
                theme = appTheme
            }, FrameLayout.LayoutParams(full, dp(56)))

            addView(FrameLayout(context).apply {

                addView(RecyclerView(context).apply {
                    id = R.id.idRecyclerView
                    layoutManager = LinearLayoutManager(context)
                    overScrollMode = View.OVER_SCROLL_NEVER
                }, FrameLayout.LayoutParams(full, full))

            }, FrameLayout.LayoutParams(full, full).apply {
                topMargin = dp(56)
            })


            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<HeaderLayout>(R.id.idHeaderLayout).apply {
            onBackListener = object : HeaderLayout.OnBackClickListener {
                override fun onBackClicked() {
                    fragmentManager?.popBackStack()
                }
            }
        }

        view.findViewById<RecyclerView>(R.id.idRecyclerView).apply {
            adapter = asmaUlHusnaAdapter
        }

        asmaUlHusnaViewModel.data.observe(this, Observer {
            asmaUlHusnaAdapter.data = it
        })

    }

    inner class AsmaUlHusnaAdapter : RecyclerView.Adapter<AsmaUlHusnaHolder>() {

        var data: List<AsmaUlHusna> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AsmaUlHusnaHolder = AsmaUlHusnaHolder(AsmaUlHusnaCell(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(72))
        })

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: AsmaUlHusnaHolder, p1: Int) {
            data.getOrNull(p1)?.let {
                p0.bindOption(it)
            }

        }
    }

    inner class AsmaUlHusnaHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindOption(asmaUlHusna: AsmaUlHusna) {
            (itemView as? AsmaUlHusnaCell)?.apply {
                nameArabic = asmaUlHusna.name?.ar
                nameLocal = String.format(string(R.string.asma_ul_husna_format)?:"",asmaUlHusna.id,asmaUlHusna.name?.uz)
                setOnClickListener {
                    (activity as? BaseActivity)?.navigationManager?.navigateToAsmaUlHusna(asmaUlHusna)
                }
            }
        }
    }
}