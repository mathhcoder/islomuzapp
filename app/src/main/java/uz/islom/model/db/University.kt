package uz.islom.model.db

import androidx.room.Entity

@Entity(tableName = "universities", primaryKeys = ["id"])
data class University(val id: Long,
                      val name: Content,
                      val type: Int,
                      val fajr: Double,
                      val isha: Double,
                      val ishaInRamadan: Double)