package uz.islom.model.preference

import android.content.SharedPreferences
import uz.islom.model.entity.Content
import uz.islom.model.entity.University
import uz.islom.model.enums.UniversityType

class UniversityPreference(private val preference: SharedPreferences) {

    var university: University
        get() {
            return University.DEFAULT
            //University(id, title, type, fajr, isha, ishaRamadan)
        }
        set(value) {
            id = value.id
            name = value.name
            type = value.type
            fajr = value.fajr
            isha = value.isha
            ishaRamadan = value.ishaRamadan

        }

    var id: Long
        get() {
            return preference.getLong("id", 0L)
        }
        set(value) {
            preference.edit().putLong("id", value).apply()
        }

    var name: Content
        get() {
            val id = preference.getLong("name_id", 0L)
            val uz = preference.getString("name_uz", "") ?: ""
            val ru = preference.getString("name_ru", "") ?: ""
            val en = preference.getString("name_en", "") ?: ""
            val ar = preference.getString("name_ar", "") ?: ""

            return Content(id, uz, ru, en, ar)
        }
        set(value) {
            preference.edit().putLong("name_id", value.id)
                    .putString("name_uz", value.uz)
                    .putString("name_ru", value.ru)
                    .putString("name_en", value.en)
                    .putString("name_ar", value.ar)
                    .apply()
        }

    var type: UniversityType
        get() {
            return UniversityType.values().getOrNull(preference.getInt("type", 0))
                    ?: UniversityType.GENERAL
        }
        set(value) {
            preference.edit().putInt("type", value.ordinal).apply()
        }

    var fajr: Float
        get() {
            return preference.getFloat("fajr", 0f)
        }
        set(value) {
            preference.edit().putFloat("fajr", value).apply()
        }

    var isha: Float
        get() {
            return preference.getFloat("isha", 0f)
        }
        set(value) {
            preference.edit().putFloat("isha", value).apply()
        }

    var ishaRamadan: Float
        get() {
            return preference.getFloat("ishaRamadan", 0f)
        }
        set(value) {
            preference.edit().putFloat("ishaRamadan", value).apply()
        }

}