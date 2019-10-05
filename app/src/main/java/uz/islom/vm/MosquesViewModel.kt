package uz.islom.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.io.net.ApiFactory
import uz.islom.io.subscribeKt
import uz.islom.model.db.Mosque

class MosquesViewModel : BaseViewModel() {

    val mosques = MutableLiveData<List<Mosque>>()


    fun getMosques(location: Location, radius: Int) {
        ApiFactory.apiService().getMosques(location.latitude, location.longitude, 10000)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeKt(Consumer {
                    mosques.postValue(it)
                })
    }


}