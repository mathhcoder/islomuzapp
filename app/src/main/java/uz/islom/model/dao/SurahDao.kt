package uz.islom.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Surah

@Dao
interface SurahDao : BaseDao {


    @Query("SELECT * FROM surahs ORDER BY id LIMIT :size OFFSET :offset")
    fun getSurahs(size : Int, offset : Int): Single<List<Surah>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(surah: Surah)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(surah: List<Surah>)


}