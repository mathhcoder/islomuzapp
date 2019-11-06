package uz.islom.model.db

import androidx.room.Dao
import java.io.Serializable

@Dao
data class Mosque(
        val id: Long,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val distance: Double?,
        val url: String?
) : Serializable