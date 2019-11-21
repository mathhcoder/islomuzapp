package uz.islom.model.entity

import androidx.room.Entity
import uz.islom.model.dm.GeoPoint

@Entity(tableName = "users", primaryKeys = ["id"])
data class User(val id: Long,
                val name: String,
                val image: String,
                val geoPoint: GeoPoint,
                val university: University,
                val madhab: Madhab) {
    companion object {
        val DEFAULT = User(0L,
                "Javohir",
                "image",
                GeoPoint.DEFAULT,
                University.DEFAULT,
                Madhab.HANAFI)
    }
}