package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.islom.model.ApiFactory
import uz.islom.model.preference.getMosqueUrl
import uz.islom.model.preference.setMosqueUrl
import uz.islom.ext.subscribeKt
import uz.islom.model.db.Mosque

class MosquesViewModel : BaseViewModel() {

    val mosques = MutableLiveData<List<Mosque>>()

    fun getMosques(latLng: LatLng) {

        setMosqueUrl("https://api.masjid.uz/api/v1/mosques/nearest")

        ApiFactory.apiService().getMosques(getMosqueUrl(), latLng.latitude, latLng.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    mosques.postValue(it)
                })
    }


}