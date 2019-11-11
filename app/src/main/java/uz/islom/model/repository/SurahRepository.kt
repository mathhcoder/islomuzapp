package uz.islom.model.repository

import io.reactivex.Single
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.api.SurahApi
import uz.islom.model.dao.AsmaUlHusnaDao
import uz.islom.model.dao.SurahDao
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Surah

class SurahRepository(private val surahApi: SurahApi,
                      private val surahDao: SurahDao) : BaseRepository() {

    fun getAll(): Single<List<Surah>> {
        return getFromNetwork().doOnSuccess { saveToDb(it) }.flatMap { getFromDB() }
    }

    private fun saveToDb(data : List<Surah>){
         surahDao.insertAll(data)
    }

    private fun getFromNetwork() : Single<List<Surah>>{
        return surahApi.getSurahs(120,0)
    }

    private fun getFromDB() : Single<List<Surah>>{
        return surahDao.getAll()
    }


}