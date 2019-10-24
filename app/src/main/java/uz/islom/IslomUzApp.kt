package uz.islom

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
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

class IslomUzApp : Application(), ViewModelStoreOwner {

    companion object {

        private lateinit var appInstance: IslomUzApp

        fun getInstance(): IslomUzApp {
            return appInstance
        }

    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appInstance = this

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }

}