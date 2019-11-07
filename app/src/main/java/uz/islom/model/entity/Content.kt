package uz.islom.model.entity

import androidx.room.Entity

@Entity(tableName = "contents", primaryKeys = ["id"])
data class Content(val id: Long,
                   val uz: String? = null,
                   val ru: String? = null,
                   val en: String? = null,
                   val ar: String? = null)