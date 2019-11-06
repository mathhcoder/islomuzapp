package uz.islom.ui

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import uz.islom.R
import uz.islom.model.enums.FunctionType
import uz.islom.model.enums.OptionType
import uz.islom.model.db.Mosque
import uz.islom.model.db.Site
import uz.islom.ui.fragment.MainFragment
import uz.islom.ui.fragment.auth.AuthorizationFragment
import uz.islom.ui.fragment.auth.RegistrationFragment
import uz.islom.ui.fragment.functions.*
import uz.islom.ui.fragment.info.SalatFragment
import uz.islom.ui.fragment.info.MosqueFragment
import uz.islom.ui.fragment.options.AboutFragment
import uz.islom.ui.fragment.options.FeedbackFragment
import uz.islom.ui.fragment.options.OfferFragment
import uz.islom.ui.fragment.options.SettingsFragment
import uz.islom.ui.fragment.other.SiteFragment


class NavigationManager {

    private var fragmentManager: FragmentManager? = null

    var navigationListener: NavigationListener? = null

    val isRootFragmentVisible = fragmentManager?.backStackEntryCount ?: 0 <= 1

    interface NavigationListener {

        fun onBackStackChanged()
    }


    fun init(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
        this.fragmentManager?.addOnBackStackChangedListener {
            if (navigationListener != null) {
                navigationListener?.onBackStackChanged()
            }
        }
    }

    private fun open(fragment: Fragment) {
        if (fragmentManager != null) {
            fragmentManager!!.beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .setCustomAnimations(R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_right)
                    .addToBackStack(fragment.toString())
                    .commit()
        }
    }

    private fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        addFragment(fragment, fragment.tag ?: "", withBackStack = true, withAnimation = false)
    }

    private fun popEveryFragment() {
        // Clear all back stack.
        val backStackCount = fragmentManager?.backStackEntryCount
        for (i in 0 until (backStackCount ?: 0)) {

            val backStackId = fragmentManager!!.getBackStackEntryAt(i).id

            fragmentManager!!.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
    }

    fun navigateBack(baseActivity: Activity) {

        if (fragmentManager?.backStackEntryCount == 0) {
            baseActivity.finish()
        } else {
            fragmentManager?.popBackStackImmediate()
        }
    }


    fun navigateToAuthorization() {
        addFragment(fragment = AuthorizationFragment.newInstance(), tag = "authorization", withBackStack = true, withAnimation = true)
    }

    fun navigateToRegistration() {
        addFragment(fragment = RegistrationFragment.newInstance(), tag = "registration", withBackStack = true, withAnimation = true)
    }

    fun navigateToMain() {
        addFragment(fragment = MainFragment(), tag = "main", withBackStack = false, withAnimation = false)
    }

    fun navigateToFunction(function: FunctionType) {



        val fragment = when (function) {

            //FunctionType.NOTIFICATION -> NotificationFragment.newInstance()

            FunctionType.KURAN -> KuranFragment.newInstance()

            FunctionType.MOSQUE -> NearMosquesFragment.newInstance()

            FunctionType.KIBLA -> QiblaFragment.newInstance()

            FunctionType.RADIO -> RadioFragment.newInstance()


            FunctionType.CALENDAR -> CalendarFragment.newInstance()

            FunctionType.MEDIA -> MediaFragment.newInstance()

            FunctionType.DUA -> DuaFragment.newInstance()

            FunctionType.ASMAUL_HUSNA -> AsmaUlHusnaFragment.newInstance()

            FunctionType.TASBIH -> TasbihFragment.newInstance()

            FunctionType.ZAKAT_CALCULATOR -> ZakatCalculatorFragment.newInstance()

          //  FunctionType.FAVOURITE -> FavouriteFragment.newInstance()

        }

        addFragment(fragment = fragment, tag = function.name, withBackStack = true, withAnimation = true)
    }

    fun navigateToOption(optionType: OptionType) {
        val fragment = when (optionType) {

            OptionType.SETTINGS -> {
                SettingsFragment.newInstance()
            }

            OptionType.FEEDBACK -> {
                FeedbackFragment.newInstance()
            }

            OptionType.OFFER -> {
                OfferFragment.newInstance()
            }

            OptionType.ABOUT -> {
                AboutFragment.newInstance()
            }

            else -> null

        }

        if (fragment != null) {

            addFragment(fragment = fragment, tag = optionType.name, withBackStack = true, withAnimation = true)

        } else when (optionType) {

            OptionType.SHARE -> {

            }

            OptionType.LOGOUT -> {

            }

            else -> {

            }
        }
    }

    fun navigateToSalats() {
        addFragment(fragment = SalatFragment.newInstance(), tag = "salats", withBackStack = true, withAnimation = true)
    }

    fun navigateToSite(site: Site) {
        addFragment(fragment = SiteFragment.newInstance(site), tag = "site:${site.id}", withBackStack = true, withAnimation = true)
    }

    fun navigateToMosqueInfo(mosque: Mosque) {
        addFragment(fragment = MosqueFragment.newInstance(mosque), tag = "mosque:${mosque.id}", withBackStack = true, withAnimation = true)
    }

    private fun addFragment(fragment: Fragment, tag: String, withBackStack: Boolean, withAnimation: Boolean) {

        val t = fragmentManager?.beginTransaction()

        if (withAnimation) {
            t?.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right)
        }

        t?.add(android.R.id.content, fragment, tag)

        if (withBackStack)
            t?.addToBackStack(tag)

        t?.commit()
    }

}