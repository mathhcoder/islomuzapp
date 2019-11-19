package uz.islom.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uz.islom.vm.UserViewModel


class MainActivity : BaseActivity() {

    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel.userUpdate.observe(this, Observer {
            if (it == null) {
                navigationManager.navigateToAuthorization()
            } else {
                navigationManager.navigateToMain()
            }
        })

    }


}