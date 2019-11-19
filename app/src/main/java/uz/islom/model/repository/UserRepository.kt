package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.UserApi
import uz.islom.model.dm.AppVersion
import uz.islom.model.dm.Device
import uz.islom.model.dm.SignInMethod
import uz.islom.model.dm.Source
import uz.islom.model.entity.User
import uz.islom.model.preference.UserPreference

data class UserRepository(val sharedPreference: UserPreference, val api: UserApi) {


    fun getUser(): Single<User> {
        Timber.i("Trying to get User")
        return getFromNetwork()
                .doOnSuccess {
                    saveToSharedPreference(it)
                }.onErrorResumeNext {
                    getFromPreference()
                }
    }

    fun auth(signInMethod: SignInMethod, appVersion: AppVersion, device: Device): Single<User> {
        return getFromNetwork()
                .doOnSuccess {
                    saveToSharedPreference(it)
                }
    }

    private fun saveToSharedPreference(user: User) {
        Timber.i("Saving user to sharedPreference user : $user")
        sharedPreference.setUser(user)
    }

    private fun getFromNetwork(): Single<User> {
        Timber.i("Trying to get user from remote")
        return api.getUser()
    }

    private fun getFromPreference(): Single<User> {
        Timber.i("Trying to get user from sharedPreference")
        return Single.just(sharedPreference.getUser())
    }


}