package uz.islom.model.preference

import android.content.SharedPreferences
import io.reactivex.Single
import uz.islom.model.entity.User


class UserPreference(private val preference: SharedPreferences) {

    fun getUser(): User? {
        val name = preference.getString("name", "") ?: ""
        val image = preference.getString("image", "") ?: ""

        return null
    }

    fun setUser(user: User) {
        val name = preference.getString("name", "") ?: ""
        val image = preference.getString("image", "") ?: ""

    }
}