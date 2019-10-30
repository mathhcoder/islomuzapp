package uz.islom.io.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import uz.islom.model.db.Mosque

interface ApiService {

    @GET
    fun getMosques(
            @Url url: String,
            @Query("lat") lat: Double,
            @Query("lon") lng: Double): Single<List<Mosque>>

}