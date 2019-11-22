package uz.islom.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.islom.ext.setNewLocale
import uz.islom.manager.AppNavigationManager

abstract class BaseActivity : AppCompatActivity() {

    val navigationManager by lazy {
        AppNavigationManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager.init(supportFragmentManager)
    }

    override fun attachBaseContext(base: Context) {

        super.attachBaseContext(base.setNewLocale(""))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}