package uz.islom.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.swipebackfragment.SwipeBackFragment
import me.yokeyword.swipebackfragment.SwipeBackLayout

abstract class SwipeAbleFragment : SwipeBackFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = attachToSwipeBack(getSwipeBackView(inflater, container, savedInstanceState))

    abstract fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeBackLayout.setEdgeOrientation(SwipeBackLayout.EDGE_LEFT)
        setEdgeLevel(SwipeBackLayout.EdgeLevel.MIN)
    }

}