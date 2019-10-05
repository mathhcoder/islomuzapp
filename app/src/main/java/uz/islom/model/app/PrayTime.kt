package uz.islom.model.app

import com.google.android.gms.maps.model.LatLng
import uz.islom.fiqh.calculateSalatTimes
import uz.islom.fiqh.fixHour
import uz.islom.io.preference.getAdjustments
import uz.islom.model.db.University
import java.util.*

class PrayTime {

    fun calculatePrayTimes(calendar: Calendar, latLng: LatLng, timeZone: TimeZone, university: University, madhab: Madhab, adjustments: Adjustments) {

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val fajrAngle = university.fajr
        val asrShadowRatio = if (madhab == Madhab.HANAFI) 2.0 else 1.0
        val timeZoneDifference = timeZone.rawOffset / 1000.0 / 3600

        val times = calculateSalatTimes(year, month, day, latLng.latitude, latLng.longitude,
                .0, timeZoneDifference, fajrAngle, asrShadowRatio, asrShadowRatio)

        val fajr = times[0] + adjustments.fajr
        val sunrise = times[1] + adjustments.sunrise
        val dhuhr = times[2] + 65 / 60 / 60 + adjustments.dhuhr // (adding 65 seconds)
        val asr = times[3] + adjustments.asr
        val sunset = times[4] + adjustments.sunset // (adding 2 minutes)
        val maghrib = sunset + 2 / 60
        val isha = times[5] + adjustments.isha
        val qiyam = fixHour(1 / 2 * (sunrise - sunset))



    }
}