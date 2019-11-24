package uz.islom.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.string
import uz.islom.model.entity.Juz
import uz.islom.ui.cell.JuzCell

class JuzListAdapter(private val onItemClickedAction: (juz: Juz) -> (Unit)) : RecyclerView.Adapter<JuzListAdapter.JuzHolder>() {

    private var data = ArrayList<Juz>()

    fun submitItems(needClean: Boolean, data: List<Juz>) {
        if (needClean) {
            this.data.clear()
            this.data.addAll(data)
        } else {
            this.data.addAll(data)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = JuzHolder(JuzCell(p0.context).apply {
        layoutParams = ViewGroup.LayoutParams(full, dp(72))
    })

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: JuzHolder, p1: Int) {
        data.getOrNull(p1)?.let {
            p0.bindItem(p1,it)
        }
    }

    inner class JuzHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(position : Int,juz: Juz) {
            (itemView as? JuzCell)?.apply {
                string(R.string.juz_title_format)?.let {
                    title = String.format(it,position+1)
                }
                string(R.string.juz_subtitle_format)?.let {
                    subtitle = String.format(it,juz.surah?.name?.uz,juz.order)
                }
            }
            itemView.setOnClickListener {
                onItemClickedAction(juz)
            }
        }
    }

}