package uz.islom.model.db

import androidx.room.Entity

@Entity(tableName = "content", primaryKeys = ["id"])
data class Content(val id: Long,
                   val uz: String? = null,
                   val ru: String? = null,
                   val en: String? = null,
                   val ar: String? = null)