package uz.islom.model.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "surahs", primaryKeys = ["id"])
data class Surah (
    val id: Long? = null,
    val name: Content? = null,
    val count: Int? = null,
    val type : Int? = null
) : Serializable