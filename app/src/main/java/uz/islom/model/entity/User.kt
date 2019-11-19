package uz.islom.model.entity

import androidx.room.Entity
import uz.islom.model.enums.MadhabType

@Entity(tableName = "users", primaryKeys = ["id"])
data class User(val id: Long,
                val name: String,
                val image: String,
                val token : String,
                val university: University,
                val madhabType: MadhabType)