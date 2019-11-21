package uz.islom.model.preference

import android.content.SharedPreferences

class AdditionalServicesPreference(private val preferences: SharedPreferences) {

    var mosqueUrl: String
        set(value) {
            preferences.edit().putString("mosque", value).apply()
        }
        get() {
            return preferences.getString("mosque", "") ?: ""
        }

    var radioUrl: String
        set(value) {
            preferences.edit().putString("radio", value).apply()
        }
        get() {
            return preferences.getString("radio", "") ?: ""
        }

    var tvUrl: String
        set(value) {
            preferences.edit().putString("tv", value).apply()
        }
        get() {
            return preferences.getString("tv", "") ?: ""
        }




}