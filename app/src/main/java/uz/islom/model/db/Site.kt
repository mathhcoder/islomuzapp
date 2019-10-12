package uz.islom.model.db

import java.io.Serializable

data class Site(
        val id: Long,
        val name: Content,
        val image: String,
        val url: String
) : Serializable