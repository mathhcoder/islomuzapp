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

    val data = MutableLiveData<List<AsmaUlHusna>>()

    private val disposable = CompositeDisposable()

    init {

        val api = retrofit.create(AsmaUlHusnaApi::class.java)
        val dao = storage.asmaUlHusnaDao()

        disposable.add(AsmaUlHusnaRepository(api,dao)
                .getAll()
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer{
                    data.postValue(it)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }

}