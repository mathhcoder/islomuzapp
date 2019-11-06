package uz.islom.ui.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.model.db.Site
import uz.islom.ui.base.SwipeAbleFragment
import uz.islom.model.enums.ThemeType

class SiteFragment : SwipeAbleFragment() {

    companion object {

        fun newInstance(site: Site) = SiteFragment().apply {
            arguments = Bundle().also {
                it.putSerializable("site", site)
            }
        }

    }

    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,appTheme: ThemeType): View? {
        return FrameLayout(inflater.context)
    }

}