package uz.islom.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.model.entity.Dua
import uz.islom.ui.cell.DuaCell

class DuaListAdapter(private val onItemClickedAction: (dua: Dua) -> (Unit)) : RecyclerView.Adapter<DuaListAdapter.DuaHolder>() {

    private var data = ArrayList<Dua>()

    fun submitItems(needClean: Boolean, data: List<Dua>) {
        if (needClean) {
            this.data.clear()
            this.data.addAll(data)
        } else {
            this.data.addAll(data)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = DuaHolder(DuaCell(p0.context).apply {
        layoutParams = ViewGroup.LayoutParams(full, dp(72))
    })

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: DuaHolder, p1: Int) {
        data.getOrNull(p1)?.let {
            p0.bindItem(it)
        }
    }


    inner class DuaHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(dua: Dua) {
            (itemView as? DuaCell)?.apply {
                order = dua.id
                nameLocal = dua.title?.uz
                description = dua.meaning?.uz
            }
            itemView.setOnClickListener {
                onItemClickedAction(dua)
            }
        }
    }

}