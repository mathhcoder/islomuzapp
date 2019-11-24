package uz.islom.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.islom.ext.dp
import uz.islom.ext.full
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.ui.cell.AsmaUlHusnaCell

class AsmaUlHusnaListAdapter(private val onAsmaSelectedAction: (asmaulHusna : AsmaUlHusna) -> (Unit)) : RecyclerView.Adapter<AsmaUlHusnaListAdapter.AsmaUlHusnaHolder>() {

    private var data = ArrayList<AsmaUlHusna>()

    fun submitItems(needClean: Boolean, data: List<AsmaUlHusna>) {
        if (needClean) {
            this.data.clear()
            this.data.addAll(data)
        } else {
            this.data.addAll(data)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = AsmaUlHusnaHolder(AsmaUlHusnaCell(p0.context).apply {
        layoutParams = ViewGroup.LayoutParams(full, dp(72))
    })

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: AsmaUlHusnaHolder, p1: Int) {
        data.getOrNull(p1)?.let {
            p0.bindItem(it)
        }
    }

    inner class AsmaUlHusnaHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(asmaUlHusna: AsmaUlHusna) {
            (itemView as? AsmaUlHusnaCell)?.apply {
                order = asmaUlHusna.id
                nameArabic = asmaUlHusna.name?.ar
                nameLocal = asmaUlHusna.name?.uz
                description = asmaUlHusna.description?.uz
            }
            itemView.setOnClickListener {
                onAsmaSelectedAction(asmaUlHusna)
            }
        }
    }
}