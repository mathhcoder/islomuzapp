package uz.islom.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import uz.islom.model.preference.UserPreference

class AppPreferenceManager(val context: Context) {

    fun getUserPreference(): UserPreference {
        val preference = context.getSharedPreferences("user", MODE_PRIVATE)
        return UserPreference(preference)
    }
}