package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.DuaApi
import uz.islom.model.dao.DuaDao
import uz.islom.model.dm.Source
import uz.islom.model.entity.Dua

class DuaRepository(private val api: DuaApi,
                    private val dao: DuaDao) : BaseRepository() {

    var isFullyLoaded = false
    lateinit var source: Source

    fun loadData(size: Int, offset: Int): Single<List<Dua>> {

        Timber.d("Trying to load duas from network with offset:$offset")

        return getFromNetwork(size, offset)
                .doOnSuccess {

                    Timber.d("Saving duas to database size : $size")

                    saveToDb(it)

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Duas fully loaded from network"); true
                    } else false

                    source = Source.NETWORK(isFullyLoaded)

                }.onErrorResumeNext {
                    Timber.d("Trying to load dua from database with offset:$offset")
                    getFromDB(size, offset)
                }.doOnSuccess {

                    isFullyLoaded = if (it.size < size) {
                        Timber.d("Dua fully loaded from DB"); true
                    } else false

                    source = Source.DATABASE(isFullyLoaded)
                }

    }

    private fun saveToDb(data: List<Dua>) {
        dao.insertAll(data)
    }

    private fun getFromNetwork(size: Int, offset: Int): Single<List<Dua>> {
        return api.getAsmaUlHusna(size, offset)
    }

    private fun getFromDB(size: Int, offset: Int): Single<List<Dua>> {
        return dao.getDuas(size, offset)
    }


}