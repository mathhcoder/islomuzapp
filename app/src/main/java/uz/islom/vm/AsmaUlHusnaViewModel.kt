package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.dm.DataResult
import uz.islom.model.dm.DataSource
import uz.islom.model.repository.AsmaUlHusnaRepository

class AsmaUlHusnaViewModel : BaseViewModel() {

    val newItemsUpdate = MutableLiveData<DataResult<AsmaUlHusna>>()
    var isLoading = true

    private val disposable = CompositeDisposable()
    private val api = networkManager.create(AsmaUlHusnaApi::class.java)
    private val dao = storageManager.asmaUlHusnaDao()
    private val repository = AsmaUlHusnaRepository(api, dao)

    fun isFullyLoaded() = repository.isFullyLoaded

    fun loadMore( offset: Int) {
        disposable.add(repository
                .loadData(PAGE_SIZE, offset)
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer {
                    newItemsUpdate.postValue(DataResult(
                            result = true,
                            dataSource = repository.source,
                            isFullyLoaded = repository.isFullyLoaded,
                            data = it,
                            errorMessage = ""))
                }, Consumer {
                    newItemsUpdate.postValue(DataResult(
                            result = false,
                            dataSource = DataSource.DATABASE,
                            isFullyLoaded = false,
                            data = emptyList(),
                            errorMessage = it.message?:""))
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }

}