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

    var isFullyLoaded = false
    lateinit var source: Source

    fun getUser(): Single<User> {
        return getFromNetwork()
                .doOnSuccess {

                    Timber.d("Saving user to sharedPreference user : $it")

                    saveToSharedPreference(it)

                    source = Source.NETWORK(isFullyLoaded)

                }.onErrorResumeNext {
                    Timber.d("Trying to get user from sharedPreference")
                    getFromPreference()
                }.doOnSuccess {
                    source = Source.DATABASE(isFullyLoaded)
                }.doOnError {
                    source = Source.ERROR()
                }
    }

    fun auth(signInMethod: SignInMethod, appVersion: AppVersion, device: Device): Single<User> {
        return getFromNetwork()
                .doOnSuccess {

                    Timber.d("Saving user to sharedPreference user : $it")

                    saveToSharedPreference(it)

                    source = Source.NETWORK(isFullyLoaded)

                }
    }

    private fun saveToSharedPreference(user: User) {
        sharedPreference.setUser(user)
    }

    private fun getFromNetwork(): Single<User> {
        return api.getUser()
    }

    private fun getFromPreference(): Single<User> {
        return Single.just(sharedPreference.getUser())
    }


}