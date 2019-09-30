package uz.islom.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.islom.R
import uz.islom.model.Functions
import uz.islom.model.Option
import uz.islom.model.Site
import uz.islom.ui.base.BaseActivity
import uz.islom.ui.screens.MainFragment
import uz.islom.ui.screens.auth.AuthorizationFragment
import uz.islom.ui.screens.auth.RegistrationFragment
import uz.islom.ui.screens.other.SiteFragment
import uz.islom.ui.screens.functions.*
import uz.islom.ui.screens.options.AboutFragment
import uz.islom.ui.screens.options.FeedbackFragment
import uz.islom.ui.screens.options.OfferFragment
import uz.islom.ui.screens.options.SettingsFragment
import uz.islom.ui.screens.other.PrayTimesFragment
import uz.islom.util.getUserToken


class MainActivity : BaseActivity(), FragmentNavigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 23)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

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

    override fun navigateToFunction(function: Functions) {
        val fragment = when (function) {

            Functions.NOTIFICATION -> NotificationFragment.newInstance()

            Functions.KURAN -> KuranFragment.newInstance()

            Functions.MOSQUE -> MosquesFragment.newInstance()

            Functions.KIBLA -> KiblaFragment.newInstance()

            Functions.RADIO -> RadioFragment.newInstance()


            Functions.CALENDAR -> CalendarFragment.newInstance()

            Functions.MEDIA -> MediaFragment.newInstance()

            Functions.DUA -> DuaFragment.newInstance()

            Functions.ASMAUL_HUSNA -> AsmaUlHusnaFragment.newInstance()

            Functions.TASBIH -> TasbihFragment.newInstance()

            Functions.ZAKAT_CALCULATOR -> ZakatCalculatorFragment.newInstance()

            Functions.FAVOURITE -> FavouriteFragment.newInstance()

        }

        addFragment(fragment = fragment, tag = function.name, withBackStack = true, withAnimation = true)
    }

    override fun navigateToOption(option: Option) {

        val fragment = when (option) {

            Option.SETTINGS -> {
                SettingsFragment.newInstance()
            }

            Option.FEEDBACK -> {
                FeedbackFragment.newInstance()
            }

            Option.OFFER -> {
                OfferFragment.newInstance()
            }

            Option.ABOUT -> {
                AboutFragment.newInstance()
            }

            else -> null

        }

        if (fragment != null) {

            addFragment(fragment = fragment, tag = option.name, withBackStack = true, withAnimation = true)

        } else when (option) {

            Option.SHARE -> {

            }

            Option.LOGOUT -> {

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