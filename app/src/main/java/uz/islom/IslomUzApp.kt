package uz.islom

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import timber.log.Timber
import uz.islom.manager.AppAlarmManager
import uz.islom.manager.AppNotificationManager
import uz.islom.manager.AppPreferenceManager

/**
 * key store pass : 1111111
 * key : key
 * pass : 1111111
 * developer -> Qodiriy
 * project -> IslomUz
 * date -> 08 Muharram 1442
 * github -> github.com/qodiriy
 */

class IslomUzApp : Application() {

    companion object {

        private lateinit var appInstance: IslomUzApp

        fun getInstance(): IslomUzApp {
            return appInstance
        }

    }

    val notificationManager by lazy {
        AppNotificationManager
    }

    val alarmManager by lazy {
        AppAlarmManager
    }

    val preferenceManager by lazy {
        AppPreferenceManager(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appInstance = this

        alarmManager.getAdhan().setNextSalatAlarm()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}