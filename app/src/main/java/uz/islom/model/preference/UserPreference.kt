package uz.islom.model.preference

import android.content.SharedPreferences
import uz.islom.model.dm.GeoPoint
import uz.islom.model.entity.University
import uz.islom.model.entity.User
import uz.islom.model.entity.Madhab


class UserPreference(private val userSharedPreference: SharedPreferences,
                     private val universityPreference: UniversityPreference) {

    var id: Long
        get() {
            return userSharedPreference.getLong("id", 0L)
        }
        set(value) {
            userSharedPreference.edit().putLong("id", value).apply()
        }

    var name: String
        get() {
            return userSharedPreference.getString("name", "") ?: ""
        }
        set(value) {
            userSharedPreference.edit().putString("name", value).apply()
        }

    var image: String
        get() {
            return userSharedPreference.getString("image", "") ?: ""
        }
        set(value) {
            userSharedPreference.edit().putString("image", value).apply()
        }

    var university: University
        get() {
            return University.DEFAULT
            //universityPreference.university
        }
        set(value) {
            universityPreference.university = value
        }

    var geoPoint: GeoPoint
        get() {
            val lat = userSharedPreference.getFloat("lat", 0f)
            val lng = userSharedPreference.getFloat("lng", 0f)
            return GeoPoint.DEFAULT
            //GeoPoint(lat, lng)
        }
        set(value) {
            userSharedPreference.edit().putFloat("lat", value.lat)
                    .putFloat("lng", value.lng)
                    .apply()
        }

    var madhab: Madhab
        get() {
            return Madhab.HANAFI
            // Madhab(userSharedPreference.getFloat("madhab", 0f))
        }
        set(value) {
            userSharedPreference.edit().putFloat("madhab", value.asrShadow).apply()
        }

    var user: User
        set(value) {
            id = value.id
            name = value.name
            image = value.image
            geoPoint = value.geoPoint
            university = value.university
            madhab = value.madhab
        }
        get() {
            return User.DEFAULT
            //User(id, name, image, geoPoint, university, madhab)
        }
}