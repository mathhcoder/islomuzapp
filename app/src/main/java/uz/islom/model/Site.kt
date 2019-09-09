package uz.islom.model

import java.io.Serializable

data class Site(
        val id: Long,
        val name: String,
        val image: String,
        val url: String
) : Serializable