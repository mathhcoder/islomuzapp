package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.api.SurahApi
import uz.islom.model.entity.Surah
import uz.islom.model.repository.SurahRepository

class SurahViewModel : BaseViewModel() {

    val data = MutableLiveData<List<Surah>>()

    private val disposable = CompositeDisposable()

    init {

        val api = networkManager.create(SurahApi::class.java)
        val dao = storageManager.surahDao()

        disposable.add(SurahRepository(api,dao)
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