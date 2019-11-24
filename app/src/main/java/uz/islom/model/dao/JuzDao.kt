package uz.islom.model.dao

import androidx.room.*
import io.reactivex.Single
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Juz
import uz.islom.model.entity.Surah

@Dao
interface JuzDao : BaseDao {

    @Query("SELECT * FROM juzs ORDER BY id LIMIT :size OFFSET :offset")
    fun getJuzs(size : Int, offset : Int): Single<List<Juz>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(juz: Juz)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(juz: List<Juz>)


}