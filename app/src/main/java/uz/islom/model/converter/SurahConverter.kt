package uz.islom.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.islom.model.entity.Content
import uz.islom.model.entity.Surah

class SurahConverter{

    @TypeConverter
    fun fromString(value: String):Surah {
        return Gson().fromJson( value, Surah::class.java)
    }

    @TypeConverter
    fun fromSurah(value: Surah): String {
        return  Gson().toJson(value)
    }

}

