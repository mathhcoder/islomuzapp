package uz.islom.ui.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.islom.model.dm.Theme

abstract class BaseFragment : Fragment() {

    internal val appTheme = Theme.GREEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    open fun onThemeChanged(theme: Theme) {

    }

    open fun onLanguageChanged(resources: Resources) {

    }

}
