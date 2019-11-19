package uz.islom.model.preference

import android.content.SharedPreferences
import io.reactivex.Single
import uz.islom.model.entity.University
import uz.islom.model.entity.User
import uz.islom.model.enums.MadhabType


class UserPreference(private val preference: SharedPreferences) {

    fun getUser(): User? {
        val name = preference.getString("name", "") ?: ""
        val image = preference.getString("image", "") ?: ""

        return User(0,"Javohir Qodiriy","","token", University.WML, MadhabType.HANAFI)
    }

    fun setUser(user: User) {
        val name = preference.getString("name", "") ?: ""
        val image = preference.getString("image", "") ?: ""

    }

    fun getToken(): String {
        return preference.getString("token", "") ?: ""
    }
}