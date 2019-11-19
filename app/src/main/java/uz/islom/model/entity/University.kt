package uz.islom.model.entity

import androidx.room.Entity
import uz.islom.model.enums.UniversityType

@Entity(tableName = "universities", primaryKeys = ["id"])
data class University(val id: Long,
                      val name: Content,
                      val type: Int,
                      val fajr: Double,
                      val isha: Double,
                      val ishaInRamadan: Double) {

    companion object {
        val WML = University(0, Content(0, "World Islamic Legue"), UniversityType.GENERAL.ordinal, 18.0, 17.0, 17.0)
    }
}