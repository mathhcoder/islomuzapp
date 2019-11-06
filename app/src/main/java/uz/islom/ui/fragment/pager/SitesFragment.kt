package uz.islom.ui.fragment.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.islom.ui.base.BaseFragment
import uz.islom.ext.full

class SitesFragment : BaseFragment() {

    companion object {
        fun newInstance() = SitesFragment().apply {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return NestedScrollView(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

            addView(LinearLayout(context).apply {

                orientation = LinearLayout.VERTICAL

                addView(RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context)
                    overScrollMode = View.OVER_SCROLL_NEVER
                    isNestedScrollingEnabled = true
                }, ViewGroup.LayoutParams(full, full))

            }, ViewGroup.LayoutParams(full, full))
        }
    }

}