package uz.islom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.swipebackfragment.SwipeBackFragment
import me.yokeyword.swipebackfragment.SwipeBackLayout
import uz.islom.model.dm.Theme

abstract class SwipeAbleFragment : SwipeBackFragment() {

    private val appTheme = Theme.GREEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = attachToSwipeBack(getSwipeBackView(inflater, container, savedInstanceState, appTheme))

    abstract fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: Theme): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeBackLayout.setEdgeOrientation(SwipeBackLayout.EDGE_LEFT)
        setEdgeLevel(SwipeBackLayout.EdgeLevel.MIN)
        onViewCreated(view,savedInstanceState,appTheme)
    }

    abstract fun onViewCreated(view: View,savedInstanceState: Bundle?,appTheme: Theme)


}