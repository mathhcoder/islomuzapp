package uz.islom.ui.functions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.ui.base.SwipeAbleFragment

class KiblaFragment : SwipeAbleFragment() {

    companion object {
        fun newInstance() = KiblaFragment()
    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(inflater.context).apply {


        }
    }

}