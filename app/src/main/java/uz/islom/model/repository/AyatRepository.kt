package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.AyatApi
import uz.islom.model.dao.AyatDao
import uz.islom.model.dm.DataSource
import uz.islom.model.entity.Ayat

class AyatRepository(private val api: AyatApi,
                     private val dao: AyatDao) : BaseRepository() {

    var isFullyLoaded = false
    lateinit var source: DataSource

    fun loadData(surahId: Long, size: Int, offset: Int): Single<List<Ayat>> {

        Timber.d("Trying to load ayats by surahId:$surahId  from network with offset:$offset")

        return getFromNetworkBySurah(surahId, size, offset)
                .doOnSuccess {

                    Timber.d("Saving ayats to database size : $size for surahId:$surahId")

                    saveToDb(it)

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Ayats fully loaded from network for surahId:$surahId"); true
                    } else false

                    source = DataSource.NETWORK

                }.onErrorResumeNext {
                    Timber.d("Trying to load ayats from database with offset:$offset for surahId:$surahId")
                    getFromDBBySurah(surahId, size, offset)
                }.doOnSuccess {

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Ayats fully loaded from DB for surahId:$surahId"); true
                    } else false

                    source = DataSource.DATABASE
                }

    }

    private fun saveToDb(data: List<Ayat>) {
        dao.insertAll(data)
    }

    private fun getFromNetworkBySurah(surahId: Long, size: Int, offset: Int): Single<List<Ayat>> {
        return api.getAyatsBySurah(surahId, size, offset)
    }

    private fun getFromDBBySurah(surahId: Long, size: Int, offset: Int): Single<List<Ayat>> {
        return dao.getAyatsBySurah(surahId, size, offset)
    }


}