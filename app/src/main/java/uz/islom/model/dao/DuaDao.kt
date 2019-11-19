package uz.islom.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.entity.Dua

@Dao
interface DuaDao : BaseDao {

    @Query("SELECT * FROM dua ORDER BY id LIMIT :size OFFSET :offset")
    fun getDuas(size : Int, offset : Int): Single<List<Dua>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dua : Dua)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asmaUlHusna: List<Dua>)


}