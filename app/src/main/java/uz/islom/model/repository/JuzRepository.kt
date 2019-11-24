package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.JuzApi
import uz.islom.model.dao.JuzDao
import uz.islom.model.dm.DataSource
import uz.islom.model.entity.Juz

class JuzRepository(private val api: JuzApi,
                    private val dao: JuzDao) : BaseRepository() {

    var isFullyLoaded = false
    lateinit var source: DataSource

    fun loadData(size: Int, offset: Int): Single<List<Juz>> {

        Timber.d("Trying to load juzs from network with offset:$offset")

        return getFromNetwork(size, offset)
                .doOnSuccess {

                    Timber.d("Saving juzs to database size : $size")

                    saveToDb(it)

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Juzs fully loaded from network"); true
                    } else false

                    source = DataSource.NETWORK

                }.onErrorResumeNext {
                    Timber.d("Trying to load juzs from database with offset:$offset")
                    getFromDB(size, offset)
                }.doOnSuccess {

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Juzs fully loaded from DB"); true
                    } else false

                    source = DataSource.DATABASE
                }

    }

    private fun saveToDb(data: List<Juz>) {
        dao.insertAll(data)
    }

    private fun getFromNetwork(size: Int, offset: Int): Single<List<Juz>> {
        return api.getJuzs(size, offset)
    }

    private fun getFromDB(size: Int, offset: Int): Single<List<Juz>> {
        return dao.getJuzs(size, offset)
    }


}