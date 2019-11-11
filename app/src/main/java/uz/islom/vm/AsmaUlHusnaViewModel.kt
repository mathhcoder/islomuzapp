package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.repository.AsmaUlHusnaRepository

class AsmaUlHusnaViewModel : BaseViewModel() {

    val newItemsUpdate = MutableLiveData<List<AsmaUlHusna>>()

    private val disposable = CompositeDisposable()
    private val api = networkManager.create(AsmaUlHusnaApi::class.java)
    private val dao = storageManager.asmaUlHusnaDao()
    private val asmaUlHusnaRepository = AsmaUlHusnaRepository(api, dao)

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