package uz.islom.manager

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import uz.islom.R
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.enums.FunctionType
import uz.islom.model.enums.OptionType
import uz.islom.ui.fragment.asma.AsmaUlHusnaDescriptionFragment
import uz.islom.ui.fragment.asma.AsmaUlHusnaFragment
import uz.islom.ui.fragment.auth.AuthorizationFragment
import uz.islom.ui.fragment.auth.RegistrationFragment
import uz.islom.ui.fragment.dua.DuaFragment
import uz.islom.ui.fragment.info.SalatFragment
import uz.islom.ui.fragment.main.MainFragment
import uz.islom.ui.fragment.mosque.NearMosquesFragment
import uz.islom.ui.fragment.qibla.QiblaFragment
import uz.islom.ui.fragment.quran.QuranFragment
import uz.islom.ui.fragment.zikr.ZikrFragment


class AppNavigationManager {

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

            FunctionType.QURAN -> QuranFragment.newInstance()

            FunctionType.MOSQUE -> NearMosquesFragment.newInstance()

            FunctionType.QIBLA -> QiblaFragment.newInstance()

            FunctionType.ZIKR -> ZikrFragment.newInstance()

            FunctionType.DUA -> DuaFragment.newInstance()


//            FunctionType.RADIO -> RadioFragment.newInstance()
//
//
//            FunctionType.CALENDAR -> CalendarFragment.newInstance()
//
//            FunctionType.MEDIA -> MediaFragment.newInstance()
//
//
            else -> AsmaUlHusnaFragment.newInstance()
//
//
//            FunctionType.ZAKAT_CALCULATOR -> ZakatCalculatorFragment.newInstance()

            //  FunctionType.FAVOURITE -> FavouriteFragment.newInstance()

        }

        addFragment(fragment = fragment, tag = function.name, withBackStack = true, withAnimation = true)
    }

    fun navigateToOption(optionType: OptionType) {
        val fragment = when (optionType) {

//            OptionType.SETTINGS -> {
//                SettingsFragment.newInstance()
//            }
//
//            OptionType.FEEDBACK -> {
//                FeedbackFragment.newInstance()
//            }
//
//            OptionType.OFFER -> {
//                OfferFragment.newInstance()
//            }
//
//            OptionType.ABOUT -> {
//                AboutFragment.newInstance()
//            }

            else -> null

        }

//        if (fragment != null) {
//
//            addFragment(fragment = fragment, tag = optionType.name, withBackStack = true, withAnimation = true)
//
//        } else when (optionType) {
//
//            OptionType.SHARE -> {
//
//            }
//
//            OptionType.LOGOUT -> {
//
//            }
//
//            else -> {
//
//            }
//        }
    }

    fun navigateToSalats() {
        addFragment(fragment = SalatFragment.newInstance(), tag = "salats", withBackStack = true, withAnimation = true)
    }

    fun navigateToAsmaUlHusna(asmaUlHusna: AsmaUlHusna) {
        addFragment(fragment = AsmaUlHusnaDescriptionFragment.newInstance(asmaUlHusna), tag = "${asmaUlHusna.javaClass.name}:${asmaUlHusna.id}", withBackStack = true, withAnimation = true)
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