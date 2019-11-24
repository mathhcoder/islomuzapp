package uz.islom.model.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Ayat
import uz.islom.model.entity.Dua
import uz.islom.model.entity.Surah

interface AyatApi {

    @GET("ayat")
    fun getAyatsBySurah(@Query("surahId") surahId: Long,
                        @Query("size") size: Int,
                        @Query("offset") offset: Int): Single<List<Ayat>>


}