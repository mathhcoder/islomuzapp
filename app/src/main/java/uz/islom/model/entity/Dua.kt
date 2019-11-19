package uz.islom.model.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "dua", primaryKeys = ["id"])
data class Dua(
        val id: Long?=null,
        val title: Content?=null,
        val body: Content?=null) : Serializable