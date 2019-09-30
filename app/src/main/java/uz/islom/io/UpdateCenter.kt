package uz.islom.io

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.islom.model.User

object UpdateCenter {

    private val newResources = MutableLiveData<Resources>()
    private val newUser = MutableLiveData<User>()


    val resources: LiveData<Resources> = newResources
    val user: LiveData<User> = newUser


}