package uz.islom.model.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Dua

interface DuaApi {

    @GET("duas")
    fun getAsmaUlHusna(@Query("size") size : Int,
                       @Query("offset") offset: Int): Single<List<Dua>>



}