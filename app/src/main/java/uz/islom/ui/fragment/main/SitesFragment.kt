package uz.islom.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ext.full
import uz.islom.ext.getColorWithAlpha
import uz.islom.model.dm.Theme

class SitesFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return NestedScrollView(inflater.context).apply {

            setBackgroundColor(appTheme.secondaryColor.getColorWithAlpha(0.7f))

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance() = SitesFragment().apply {}
    }


}