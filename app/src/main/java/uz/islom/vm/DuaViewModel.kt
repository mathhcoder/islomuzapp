package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.api.DuaApi
import uz.islom.model.entity.Dua
import uz.islom.model.repository.AsmaUlHusnaRepository
import uz.islom.model.repository.DuaRepository

class DuaViewModel : BaseViewModel() {

    val newItemsUpdate = MutableLiveData<List<Dua>>()

    private val disposable = CompositeDisposable()
    private val api = networkManager.create(DuaApi::class.java)
    private val dao = storageManager.duaDao()
    private val asmaUlHusnaRepository = DuaRepository(api, dao)

    fun isFullyLoaded() = asmaUlHusnaRepository.isFullyLoaded

    fun loadMore(size: Int, offset: Int) {
        disposable.add(asmaUlHusnaRepository
                .loadAsmaUlHusna(size, offset)
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer {
                    newItemsUpdate.postValue(it)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }

}