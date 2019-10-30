package uz.islom.model.db

import java.io.Serializable

data class Mosque(
        val id: Long,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val distance : Double,
        val url : String
) : Serializable