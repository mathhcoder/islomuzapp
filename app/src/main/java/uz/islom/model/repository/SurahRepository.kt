package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.SurahApi
import uz.islom.model.dao.SurahDao
import uz.islom.model.dm.DataSource
import uz.islom.model.entity.Surah

class SurahRepository(private val api: SurahApi,
                      private val dao: SurahDao) : BaseRepository() {

    var isFullyLoaded = false
    var source: DataSource = DataSource.NETWORK

    fun loadData(size: Int, offset: Int): Single<List<Surah>> {
        Timber.d("Trying to load surahs from network with offset:$offset")

        return getFromNetwork(size, offset).doOnSuccess {

            Timber.d("Saving surahs to database size : $size")

            saveToDb(it)

            isFullyLoaded = if (it.size < size) {
                Timber.d("Surahs fully loaded"); true
            } else false

            source = DataSource.NETWORK

        }.onErrorResumeNext {

            Timber.d("Trying to load surahs from database with offset:$offset")

            getFromDB(size, offset)

        }.doOnSuccess {

            isFullyLoaded = if (it.size < size) {
                Timber.d("Surahs fully loaded"); true
            } else false

            source = DataSource.DATABASE

        }

    }

    private fun saveToDb(data: List<Surah>) {
        dao.insertAll(data)
    }

    private fun getFromNetwork(size: Int, offset: Int): Single<List<Surah>> {
        return api.getSurahs(size, offset)
    }

    private fun getFromDB(size: Int, offset: Int): Single<List<Surah>> {
        return dao.getSurahs(size, offset)
    }


}