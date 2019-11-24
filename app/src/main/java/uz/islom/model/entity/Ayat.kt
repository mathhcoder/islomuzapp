package uz.islom.model.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "ayats", primaryKeys = ["id"])
data class Ayat( val id: Long? = null,
                 val surahId: Long? = null,
                 val order: Int? = null,
                 val page: Int? = null,
                 val body: String? = null,
                 val meaning: Content? = null) : Serializable