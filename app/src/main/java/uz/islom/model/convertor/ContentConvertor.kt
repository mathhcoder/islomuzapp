package uz.islom.model.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.islom.model.db.Content

class ContentConvertor{

    @TypeConverter
    fun fromString(value: String): Content {
        return Gson().fromJson( value,Content::class.java)
    }

    @TypeConverter
    fun fromContent(value: Content): String {
        return  Gson().toJson(value)
    }

}

