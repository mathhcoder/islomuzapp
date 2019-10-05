package uz.islom.io.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uz.islom.model.db.Mosque

interface ApiService {

    @GET("mosques")
    fun getMosques(@Query("lat") lat: Double,
                   @Query("lng") lng: Double,
                   @Query("distance") radius: Int): Single<List<Mosque>>


}