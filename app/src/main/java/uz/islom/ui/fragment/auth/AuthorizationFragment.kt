package uz.islom.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ext.full
import uz.islom.model.dm.Theme

class AuthorizationFragment : BaseFragment() {

    companion object {
        fun newInstance() = AuthorizationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FrameLayout(inflater.context).apply {

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

}