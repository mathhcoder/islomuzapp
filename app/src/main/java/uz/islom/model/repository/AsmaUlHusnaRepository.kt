package uz.islom.model.repository

import io.reactivex.Single
import timber.log.Timber
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.dao.AsmaUlHusnaDao
import uz.islom.model.dm.DataSource
import uz.islom.model.entity.AsmaUlHusna

class AsmaUlHusnaRepository(private val api: AsmaUlHusnaApi,
                            private val dao: AsmaUlHusnaDao) : BaseRepository() {

    var isFullyLoaded = false
    var source: DataSource = DataSource.NETWORK

    fun loadData(size: Int, offset: Int): Single<List<AsmaUlHusna>> {
        Timber.d("Trying to load asmaUlHusna from network with offset:$offset")

        return getFromNetwork(size, offset).doOnSuccess {

            Timber.d("Saving asmaUlHusna to database size : $size")

            saveToDb(it)

            isFullyLoaded = if (it.size < size) {
                Timber.d("AsmaUlHusna fully loaded"); true
            } else false

            source = DataSource.NETWORK

        }.onErrorResumeNext {

            Timber.d("Trying to load asmaUlHusna from database with offset:$offset")

            getFromDB(size, offset)

        }.doOnSuccess {

            isFullyLoaded = if (it.size < size) {
                Timber.d("AsmaUlHusna fully loaded"); true
            } else false

            source = DataSource.DATABASE

        }

    }

    private fun saveToDb(data: List<AsmaUlHusna>) {
        dao.insertAll(data)
    }

    private fun getFromNetwork(size: Int, offset: Int): Single<List<AsmaUlHusna>> {
        return api.getAsmaUlHusna(size, offset)
    }

    private fun getFromDB(size: Int, offset: Int): Single<List<AsmaUlHusna>> {
        return dao.getAsmaUlHusna(size, offset)
    }

}