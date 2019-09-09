package uz.islom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import uz.islom.R
import uz.islom.model.Function
import uz.islom.model.Site
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.fragments.MainFragment
import uz.islom.ui.fragments.SiteFragment
import uz.islom.ui.fragments.functions.KiblaFragment
import uz.islom.ui.fragments.functions.TasbihFragment


class MainActivity : BaseActivity(), FragmentNavigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(fragment = MainFragment(), tag = "main", withBackStack = false, withAnimation = false)

    }

    override fun navigateToFunction(function: Function) {
        val fragment = when (function) {
            Function.KIBLA -> {
                KiblaFragment.newInstance()
            }

            Function.TASBIH -> {
                TasbihFragment.newInstance()
            }

            else -> {
                KiblaFragment.newInstance()
            }
        }

        addFragment(fragment = fragment, tag = function.name, withBackStack = true, withAnimation = true)
    }

    override fun navigateToSite(site: Site) {
        addFragment(fragment = SiteFragment.newInstance(site), tag = "site:${site.id}", withBackStack = true, withAnimation = true)
    }


    private fun addFragment(fragment: Fragment, tag: String, withBackStack: Boolean, withAnimation: Boolean) {

        val t = supportFragmentManager.beginTransaction()

        if (withAnimation) {
            t.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right)
        }

        t.add(android.R.id.content, fragment, tag)

        if (withBackStack)
            t.addToBackStack(tag)

        t.commit()
    }


}