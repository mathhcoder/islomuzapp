package uz.islom.model.db

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "sites", primaryKeys = ["id"])
data class Site(
        val id: Long,
        val name: Content,
        val image: String,
        val url: String
) : Serializable