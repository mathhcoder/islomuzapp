package uz.islom.model.db

import java.io.Serializable

data class Site(
        val id: Long,
        val name: String,
        val image: String,
        val url: String
) : Serializable