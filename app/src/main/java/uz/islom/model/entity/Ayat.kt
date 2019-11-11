package uz.islom.model.entity

import java.io.Serializable

data class Ayat( val id: Long? = null,
                 val surahId: Long? = null,
                 val order: Int? = null,
                 val page: Int? = null,
                 val body: String? = null,
                 val meaning: Content? = null) : Serializable