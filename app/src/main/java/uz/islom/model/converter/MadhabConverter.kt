package uz.islom.model.converter

import androidx.room.TypeConverter
import uz.islom.model.entity.Madhab

class MadhabConverter {

    @TypeConverter
    fun fromString(value: String): Madhab {
        return Madhab(value.toFloat())
    }

    @TypeConverter
    fun fromMadhab(value: Madhab): String {
        return value.asrShadow.toString()
    }

}