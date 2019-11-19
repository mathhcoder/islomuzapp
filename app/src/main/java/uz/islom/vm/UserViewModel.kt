package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.api.UserApi
import uz.islom.model.entity.User
import uz.islom.model.repository.UserRepository

class UserViewModel : BaseViewModel() {

    val userUpdate = MutableLiveData<User?>()

    private val disposable = CompositeDisposable()
    private val api = networkManager.create(UserApi::class.java)
    private val preferences = preferenceManager.getUserPreference()
    private val repository = UserRepository(preferences, api)

    init {
        getUser()
    }


    private fun getUser() {
        disposable.add(repository
                .getUser()
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer {
                    userUpdate.postValue(it)
                }, Consumer {
                    userUpdate.postValue(null)
                }))
    }

    fun auth() {
        disposable.add(repository
                .getUser()
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer {
                    userUpdate.postValue(it)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }
}