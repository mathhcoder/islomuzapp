package uz.islom.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import uz.islom.model.preference.AdditionalServicesPreference
import uz.islom.model.preference.AdjustmentPreference
import uz.islom.model.preference.UniversityPreference
import uz.islom.model.preference.UserPreference

class AppPreferenceManager(val context: Context) {

    fun getUniversityPreference(): UniversityPreference {
        val preferences = context.getSharedPreferences("university", MODE_PRIVATE)
        return UniversityPreference(preferences)
    }

    fun getAdditionalServicesPreference(): AdditionalServicesPreference {
        val preferences = context.getSharedPreferences("additional_services", MODE_PRIVATE)
        return AdditionalServicesPreference(preferences)
    }

    fun getUserPreference(): UserPreference {
        val preference = context.getSharedPreferences("user", MODE_PRIVATE)
        return UserPreference(preference, getUniversityPreference())
    }

    fun getAdjustmentPreference(): AdjustmentPreference {
        val preference = context.getSharedPreferences("adjustment", MODE_PRIVATE)
        return AdjustmentPreference(preference)

    }
}