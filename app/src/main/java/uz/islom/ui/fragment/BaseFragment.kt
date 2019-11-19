package uz.islom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.islom.model.dm.Theme

abstract class BaseFragment : Fragment() {

    private val appTheme = Theme.GREEN

    abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, appTheme: Theme): View

    abstract fun onViewCreated(view: View, savedInstanceState: Bundle?, appTheme: Theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onCreateView(inflater, container, savedInstanceState, appTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(view, savedInstanceState, appTheme)
    }

}
