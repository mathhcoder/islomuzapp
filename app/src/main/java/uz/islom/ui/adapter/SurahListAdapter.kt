package uz.islom.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.islom.R
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.ext.string
import uz.islom.model.entity.Dua
import uz.islom.model.entity.Surah
import uz.islom.ui.cell.DuaCell
import uz.islom.ui.cell.SurahCell

class SurahListAdapter(private val onItemClickedAction: (surah: Surah) -> (Unit)) : RecyclerView.Adapter<SurahListAdapter.SurahHolder>() {

    private var data = ArrayList<Surah>()

    fun submitItems(needClean: Boolean, data: List<Surah>) {
        if (needClean) {
            this.data.clear()
            this.data.addAll(data)
        } else {
            this.data.addAll(data)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = SurahHolder(SurahCell(p0.context).apply {
        layoutParams = ViewGroup.LayoutParams(full, dp(72))
    })

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: SurahHolder, p1: Int) {
        data.getOrNull(p1)?.let {
            p0.bindItem(it)
        }
    }


    inner class SurahHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(surah: Surah) {
            (itemView as? SurahCell)?.apply {
                order = surah.id
                nameArabic = surah.name?.ar
                nameLocal = surah.name?.uz

                description = string(R.string.surah_subtitle_format)?.let {
                    val city = if (surah.type == 0) string(R.string.surah_city_makkah) else string(R.string.surah_city_madina)
                    String.format(it, city ?: "", surah.count)
                } ?: ""
            }
            itemView.setOnClickListener {
                onItemClickedAction(surah)
            }
        }
    }

}