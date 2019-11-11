package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.ext.subscribeKt
import uz.islom.model.api.MosqueApi
import uz.islom.model.entity.Mosque
import uz.islom.model.preference.getMosqueUrl
import uz.islom.model.preference.setMosqueUrl

class MosquesViewModel : BaseViewModel() {

    val mosques = MutableLiveData<List<Mosque>>()

    fun getMosques(latLng: LatLng) {

        setMosqueUrl("https://api.masjid.uz/api/v1/mosques/nearest")

        networkManager.create(MosqueApi::class.java).getMosques(getMosqueUrl(), latLng.latitude, latLng.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    mosques.postValue(it)
                })
    }


}