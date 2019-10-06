package uz.islom.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.islom.R
import uz.islom.model.app.FunctionType
import uz.islom.model.app.OptionType
import uz.islom.model.db.Mosque
import uz.islom.model.db.Site
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.screens.MainFragment
import uz.islom.ui.screens.auth.AuthorizationFragment
import uz.islom.ui.screens.auth.RegistrationFragment
import uz.islom.ui.screens.other.SiteFragment
import uz.islom.ui.screens.functions.*
import uz.islom.ui.screens.info.MosqueInfoFragment
import uz.islom.ui.screens.options.AboutFragment
import uz.islom.ui.screens.options.FeedbackFragment
import uz.islom.ui.screens.options.OfferFragment
import uz.islom.ui.screens.options.SettingsFragment
import uz.islom.ui.screens.other.PrayTimesFragment
import uz.islom.util.getUserToken


class MainActivity : BaseActivity(), FragmentNavigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= 23)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        if (getUserToken().isNotEmpty())
            navigateToMain()
        else navigateToAuthorization()

    }

    override fun navigateToAuthorization() {
        addFragment(fragment = AuthorizationFragment.newInstance(), tag = "authorization", withBackStack = true, withAnimation = true)
    }

    override fun navigateToRegistration() {
        addFragment(fragment = RegistrationFragment.newInstance(), tag = "registration", withBackStack = true, withAnimation = true)
    }

    override fun navigateToMain() {
        addFragment(fragment = MainFragment(), tag = "main", withBackStack = false, withAnimation = false)
    }

    override fun navigateToFunction(function: FunctionType) {
        val fragment = when (function) {

            FunctionType.NOTIFICATION -> NotificationFragment.newInstance()

            FunctionType.KURAN -> KuranFragment.newInstance()

            FunctionType.MOSQUE -> NearMosquesFragment.newInstance()

            FunctionType.KIBLA -> KiblaFragment.newInstance()

            FunctionType.RADIO -> RadioFragment.newInstance()


            FunctionType.CALENDAR -> CalendarFragment.newInstance()

            FunctionType.MEDIA -> MediaFragment.newInstance()

            FunctionType.DUA -> DuaFragment.newInstance()

            FunctionType.ASMAUL_HUSNA -> AsmaUlHusnaFragment.newInstance()

            FunctionType.TASBIH -> TasbihFragment.newInstance()

            FunctionType.ZAKAT_CALCULATOR -> ZakatCalculatorFragment.newInstance()

            FunctionType.FAVOURITE -> FavouriteFragment.newInstance()

        }

        addFragment(fragment = fragment, tag = function.name, withBackStack = true, withAnimation = true)
    }

    override fun navigateToOption(optionType: OptionType) {

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

    override fun navigateToSite(site: Site) {
        addFragment(fragment = SiteFragment.newInstance(site), tag = "site:${site.id}", withBackStack = true, withAnimation = true)
    }

    override fun navigateToSalats() {
        addFragment(fragment = PrayTimesFragment.newInstance(), tag = "salats", withBackStack = true, withAnimation = true)
    }

    override fun navigateToMosqueInfo(mosque: Mosque) {
        addFragment(fragment = MosqueInfoFragment.newInstance(mosque), tag = "mosque:${mosque.id}", withBackStack = true, withAnimation = true)
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