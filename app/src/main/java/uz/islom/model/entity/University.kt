package uz.islom.model.entity

import androidx.room.Entity
import uz.islom.model.enums.UniversityType

@Entity(tableName = "universities", primaryKeys = ["id"])
data class University(val id: Long,
                      val name: Content,
                      val type: UniversityType,
                      val fajr: Float,
                      val isha: Float,
                      val ishaRamadan: Float) {

    companion object {
        val DEFAULT = University(0, Content(0, "World Islamic Legue"), UniversityType.GENERAL, 18f, 17f, 17f)
    }
}