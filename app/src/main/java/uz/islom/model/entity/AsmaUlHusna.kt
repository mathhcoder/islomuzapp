package uz.islom.model.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "asmaulhusna", primaryKeys = ["id"])
data class AsmaUlHusna(
        val id: Long?=null,
        val name: Content?=null,
        val description: Content?=null) : Serializable