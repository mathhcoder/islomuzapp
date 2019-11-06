package uz.islom.model.api

import io.reactivex.Single
import retrofit2.http.GET
import uz.islom.model.`object`.AsmaUlHusna

interface AsmaUlHusnaApi {

    @GET("asmaulhusnas")
    fun getAll(): Single<List<AsmaUlHusna>>

}