package uz.islom.android

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

fun Context.setNewLocale(language: String): Context {
    return updateResources(language)
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

