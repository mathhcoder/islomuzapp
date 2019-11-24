package uz.islom.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.string
import uz.islom.ext.wrap
import uz.islom.model.entity.Ayat
import uz.islom.model.entity.Dua
import uz.islom.ui.cell.AyatCell
import uz.islom.ui.cell.DuaCell

class AyatListAdapter : RecyclerView.Adapter<AyatListAdapter.AyatHolder>() {

    private var data = ArrayList<Ayat>()

    fun submitItems(needClean: Boolean, data: List<Ayat>) {
        if (needClean) {
            this.data.clear()
            this.data.addAll(data)
        } else {
            this.data.addAll(data)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = AyatHolder(AyatCell(p0.context).apply {
        layoutParams = ViewGroup.LayoutParams(full, wrap)
    })

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: AyatHolder, p1: Int) {
        data.getOrNull(p1)?.let {
            p0.bindItem(it)
        }
    }


    inner class AyatHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(ayat: Ayat) {
            (itemView as? AyatCell)?.apply {
                arabic = ayat.meaning?.ar
                string(R.string.ayat_meaning_format)?.let {
                    meaning = String.format(it, ayat.order, ayat.meaning?.uz)
                }

            }
        }
    }

}