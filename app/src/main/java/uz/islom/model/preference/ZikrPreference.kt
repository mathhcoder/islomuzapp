package uz.islom.model.preference

import android.content.SharedPreferences

class ZikrPreference(private val preferences: SharedPreferences) {

    var typeMax: Int
        set(value) {
            preferences.edit().putInt("type_max", value).apply()
        }
        get() {
            return preferences.getInt("type_max", 33)
        }

    var max: Int
        set(value) {
            preferences.edit().putInt("max", value).apply()
        }
        get() {
            return preferences.getInt("max", 99)
        }

    var reaction: Int
        set(value) {
            preferences.edit().putInt("reaction", value).apply()
        }
        get() {
            return preferences.getInt("reaction", 0)
        }
}