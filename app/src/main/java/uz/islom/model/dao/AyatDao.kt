package uz.islom.model.dao

import androidx.room.*
import io.reactivex.Single
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Ayat
import uz.islom.model.entity.Juz
import uz.islom.model.entity.Surah

@Dao
interface AyatDao : BaseDao {

    @Query("SELECT * FROM ayats WHERE surahId=:surahId ORDER BY id LIMIT :size OFFSET :offset")
    fun getAyatsBySurah(surahId : Long, size : Int, offset : Int): Single<List<Ayat>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ayat: Ayat)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ayats: List<Ayat>)


}