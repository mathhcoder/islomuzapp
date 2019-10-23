package uz.islom.ui.screens.functions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.android.colour
import uz.islom.android.string
import uz.islom.model.db.AsmaUlHusna
import uz.islom.ui.base.BaseImageButton
import uz.islom.ui.base.BaseTextView
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.ui.cells.AsmaUlHusnaCell
import uz.islom.ui.util.dp
import uz.islom.ui.util.full
import uz.islom.ui.util.setTextSizeSp

class AsmaUlHusnaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = AsmaUlHusnaFragment()
    }

    private val asmaUlHusnaAdapter = AsmaUlHusnaAdapter()

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(inflater.context).apply {

            addView(FrameLayout(context).apply {

                addView(BaseImageButton(context).apply {
                    id = R.id.backButton
                    setButtonPadding(dp(16))
                }, ViewGroup.LayoutParams(dp(56), dp(56)))

                addView(BaseTextView(context).apply {
                    id = R.id.titleView
                    gravity = Gravity.CENTER_VERTICAL
                    text = string(R.string.asmaul_husna)
                    setTextSizeSp(18)
                }, FrameLayout.LayoutParams(full, full).apply {
                    leftMargin = dp(72)
                    rightMargin = dp(16)
                })

            }, ViewGroup.LayoutParams(full, dp(56)))

            addView(FrameLayout(context).apply {

                addView(RecyclerView(context).apply {
                    id = R.id.recyclerView
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

        view.findViewById<BaseImageButton>(R.id.backButton).apply {
            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
            setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = asmaUlHusnaAdapter
        }

    }

    inner class AsmaUlHusnaAdapter : RecyclerView.Adapter<AsmaUlHusnaHolder>() {

        var data: List<AsmaUlHusna> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AsmaUlHusnaHolder = AsmaUlHusnaHolder(AsmaUlHusnaCell(p0.context).apply {
            layoutParams = ViewGroup.LayoutParams(full, dp(64))
        })

        override fun onViewAttachedToWindow(holder: AsmaUlHusnaHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(p0: AsmaUlHusnaHolder, p1: Int) {
            p0.bindOption(data[p1])
        }
    }

    inner class AsmaUlHusnaHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindOption(asmaUlHusna: AsmaUlHusna) {
            //   (itemView as? AsmaUlHusnaCell)?.optionImage = drawable(option.imageRes)
            //  (itemView as? AsmaUlHusnaCell)?.optionName = string(option.nameRes) ?: ""
        }
    }
}