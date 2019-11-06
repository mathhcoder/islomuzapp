package uz.islom.model.`object`

import androidx.room.Entity
import uz.islom.model.db.Content
import java.io.Serializable

@Entity(tableName = "asmaulhusna", primaryKeys = ["id"])
data class AsmaUlHusna(
        val id: Long?=null,
        val name: Content?=null,
        val description: Content?=null) : Serializable