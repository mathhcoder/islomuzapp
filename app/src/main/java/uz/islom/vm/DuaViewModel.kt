package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.api.DuaApi
import uz.islom.model.dm.DataResult
import uz.islom.model.dm.DataSource
import uz.islom.model.entity.Dua
import uz.islom.model.repository.DuaRepository

class DuaViewModel : BaseViewModel() {

    val newItemsUpdate = MutableLiveData<DataResult<Dua>>()
    var isLoading = true

    private val disposable = CompositeDisposable()
    private val api = networkManager.create(DuaApi::class.java)
    private val dao = storageManager.duaDao()
    private val repository = DuaRepository(api, dao)

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