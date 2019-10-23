package uz.islom.model.db

import androidx.room.Entity

@Entity(tableName = "asmaulhusna", primaryKeys = ["id"])
data class AsmaUlHusna(
        val id: Long,
        val name: Content,
        val image: String,
        val description: Content) {

}