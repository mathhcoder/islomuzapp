package uz.islom.model.db

import java.io.Serializable

data class Mosque(
        val id: Long,
        val name: String,
        val lat: Double,
        val lng: Double
) : Serializable