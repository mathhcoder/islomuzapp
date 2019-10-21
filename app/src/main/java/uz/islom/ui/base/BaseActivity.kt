package uz.islom.ui.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.islom.android.setNewLocale
import uz.islom.ui.NavigationManager

abstract class BaseActivity : AppCompatActivity() {

    val navigationManager by lazy {
        NavigationManager()
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