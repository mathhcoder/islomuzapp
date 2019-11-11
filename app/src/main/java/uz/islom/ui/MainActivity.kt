package uz.islom.ui

import android.os.Bundle
import uz.islom.model.preference.getUserToken

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getUserToken().isNotEmpty()) {
            navigationManager.navigateToMain()
        } else {
            navigationManager.navigateToAuthorization()
        }



    }

}