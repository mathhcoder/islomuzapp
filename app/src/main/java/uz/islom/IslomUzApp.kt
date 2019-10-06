package uz.islom

import android.app.Application
import timber.log.Timber

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


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}