package uz.islom.model.entity

import androidx.room.Entity

@Entity(tableName = "universities", primaryKeys = ["id"])
data class University(val id: Long,
                      val name: Content,
                      val type: Int,
                      val fajr: Double,
                      val isha: Double,
                      val ishaInRamadan: Double)