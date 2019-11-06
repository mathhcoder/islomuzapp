package uz.islom.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.islom.model.`object`.AsmaUlHusna

@Dao
interface AsmaUlHusnaDao : BaseDao {


    @Query("SELECT * FROM asmaulhusna")
    fun getAll(): Single<List<AsmaUlHusna>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asmaUlHusna: AsmaUlHusna)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asmaUlHusna: List<AsmaUlHusna>)


}