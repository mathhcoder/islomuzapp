package uz.islom.model.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "juzs", primaryKeys = ["id"])
data class Juz(val id: Long? = null,
               val surah: Surah? = null,
               val body: String? = null,
               val page: Int? = null,
               val order : Int? = null,
               val meaning: Content? = null) : Serializable