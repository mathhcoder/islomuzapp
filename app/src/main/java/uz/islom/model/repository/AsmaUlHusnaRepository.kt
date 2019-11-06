package uz.islom.model.repository

import io.reactivex.Flowable
import io.reactivex.Single
import uz.islom.model.`object`.AsmaUlHusna
import uz.islom.model.api.AsmaUlHusnaApi
import uz.islom.model.dao.AsmaUlHusnaDao

class AsmaUlHusnaRepository(private val asmaulHusnaApi: AsmaUlHusnaApi,
                            private val asmaUlHusnaDao: AsmaUlHusnaDao) : BaseRepository() {


    fun getAll(): Flowable<List<AsmaUlHusna>> {
        return Single.concatArray(getFromNetwork(), getFromDB())

    }

    private fun getFromNetwork() : Single<List<AsmaUlHusna>>{
        return asmaulHusnaApi.getAll().doOnSuccess {
            asmaUlHusnaDao.insertAll(it)
        }
    }

    private fun getFromDB() : Single<List<AsmaUlHusna>>{
        return asmaUlHusnaDao.getAll()
    }


}