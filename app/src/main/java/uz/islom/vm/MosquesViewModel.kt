package uz.islom.vm

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import uz.islom.ext.subscribeKt
import uz.islom.model.api.MosqueApi
import uz.islom.model.entity.Mosque
import uz.islom.model.repository.MosqueRepository

class MosquesViewModel : BaseViewModel() {

    val mosquesData = MutableLiveData<List<Mosque>>()

    private val disposable = CompositeDisposable()

    private val api = networkManager.create(MosqueApi::class.java)
    private val preferences = preferenceManager.getAdditionalServicesPreference()
    private val repository = MosqueRepository(dispatchers, api, preferences)

    fun getMosques(latLng: LatLng) {

        disposable.add(repository.getNearMosques(latLng.latitude.toFloat(), latLng.longitude.toFloat())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeKt(Consumer {
                    mosquesData.postValue(it)
                }))
    }


}