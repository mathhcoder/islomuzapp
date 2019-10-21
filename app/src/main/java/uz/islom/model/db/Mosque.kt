package uz.islom.model.db

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "mosques", primaryKeys = ["id"])
data class Mosque(
        val id: Long,
        val name: String,
        val lat: Double,
        val lng: Double
) : Serializable