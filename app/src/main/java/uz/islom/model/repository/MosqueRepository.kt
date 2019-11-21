package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.MosqueApi
import uz.islom.model.dm.Dispatchers
import uz.islom.model.entity.Mosque
import uz.islom.model.preference.AdditionalServicesPreference


class MosqueRepository(private val threadManager: Dispatchers,
                       private val api: MosqueApi,
                       private val preference: AdditionalServicesPreference) : BaseRepository() {

    fun getNearMosques(lat: Float, lng: Float): Single<List<Mosque>> {
        Timber.d("Trying to get mosquesData near lat:$lat lang:$lng")
        return getMosqueUrl().flatMap {
            getFromNetwork(it, lat, lng)
        }
    }

    private fun getMosqueUrl(): Single<String> {
        return Single.just(preference.mosqueUrl)
                .subscribeOn(threadManager.preference)

    }

    private fun getFromNetwork(url: String, lat: Float, lng: Float): Single<List<Mosque>> {
        return api.getMosques(url, lat, lng)
                .subscribeOn(threadManager.network)
    }


}