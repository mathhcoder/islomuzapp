package uz.islom.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import timber.log.Timber
import uz.islom.vm.UserViewModel


class MainActivity : BaseActivity() {

    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("Main Activity Launched ${System.currentTimeMillis()}")

//        userViewModel.userUpdate.observe(this, Observer {
//            if (it == null) {
//                navigationManager.navigateToAuthorization()
//            } else {
                navigationManager.navigateToMain()
      //      }
      //  })

    }


}