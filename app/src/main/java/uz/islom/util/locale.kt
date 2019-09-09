package uz.islom.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import java.util.*


/**
 * developer -> Qodiriy
 * project -> App
 * date -> 20 May 2019
 * github -> github.com/qodiriy
 */


fun Context.setLocal(): Context {
    return setNewLocale(getLanguage())
}

fun Fragment.getLanguage() = context?.getLanguage()

fun Context.getLanguage() = PreferenceManager
        .getDefaultSharedPreferences(this).getString("currentLanguage", "uz") ?: "uz"


fun Context.setNewLocale(language: String): Context {
    setLanguage(language)
    return updateResources(language)
}

private fun Context.setLanguage(language: String) {
    PreferenceManager.getDefaultSharedPreferences(this).edit().putString("currentLanguage", language).apply()
}

private fun Context.updateResources(language: String): Context {

    var c = this
    val locale = Locale(language)
    Locale.setDefault(locale)

    val res = c.resources
    val config = Configuration(res.configuration)
    if (Build.VERSION.SDK_INT >= 17) {
        config.setLocale(locale)
        c = this.createConfigurationContext(config)
    } else {
        config.locale = locale
        res.updateConfiguration(config, res.displayMetrics)
    }
    return c

}

