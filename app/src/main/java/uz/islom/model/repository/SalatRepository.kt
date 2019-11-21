package uz.islom.model.repository

import timber.log.Timber
import uz.islom.fiqh.calculateSalatTimes
import uz.islom.model.dm.Adjustments
import uz.islom.model.dm.GeoPoint
import uz.islom.model.dm.Salat
import uz.islom.model.entity.Madhab
import uz.islom.model.entity.University
import uz.islom.model.enums.NotificationType
import uz.islom.model.enums.SalatType
import uz.islom.model.preference.AdjustmentPreference
import uz.islom.model.preference.UniversityPreference
import uz.islom.model.preference.UserPreference
import java.util.*

class SalatRepository(private val userPreference: UserPreference,
                      private val adjustmentPreference: AdjustmentPreference) {

    fun getSalatTimes(date: Calendar): ArrayList<Salat> {

        Timber.d("Calculating prayTimes for time : $date")

        val latLng = userPreference.geoPoint
        val timeZone = TimeZone.getDefault()
        val university = userPreference.university
        val madhab = userPreference.madhab
        val adjustments = adjustmentPreference.adjustments

        val salatTimes = calculatePrayTimes(date, timeZone, latLng, university, madhab, adjustments)

        val lastIsha = Salat(0, SalatType.LAST_ISHA, salatTimes[0], false, 0, NotificationType.DEFAULT)
        val fajr = Salat(0, SalatType.FAJR, salatTimes[1], false, 0, NotificationType.DEFAULT)
        val sunrise = Salat(0, SalatType.SUNRISE, salatTimes[2], false, 0, NotificationType.NONE)
        val dhuhr = Salat(0, SalatType.DHUHR, salatTimes[3], false, 0, NotificationType.SILENT)
        val asr = Salat(0, SalatType.ASR, salatTimes[4], false, 0, NotificationType.SILENT)
        val maghrib = Salat(0, SalatType.MAGHRIB, salatTimes[5], false, 0, NotificationType.DEFAULT)
        val isha = Salat(0, SalatType.ISHA, salatTimes[6], false, 0, NotificationType.DEFAULT)
        val nextFajr = Salat(0, SalatType.NEXT_FAJR, salatTimes[7], false, 0, NotificationType.DEFAULT)

        return arrayListOf(lastIsha, fajr, sunrise, dhuhr, asr, maghrib, isha, nextFajr)
    }


    private fun calculatePrayTimes(date: Calendar, timeZone: TimeZone, geoPoint: GeoPoint, university: University, madhab: Madhab, adjustments: Adjustments): List<Calendar> {

        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)

        val fajrAngle = university.fajr.toDouble()
        val ishaAngle = university.isha.toDouble()
        val asrShadowRatio = madhab.asrShadow.toDouble()

        val times = calculateSalatTimes(year, month, day, timeZone.rawOffset.toDouble() / 1000 / 3600, geoPoint.lat.toDouble(),
                geoPoint.lng.toDouble(), .0, fajrAngle, asrShadowRatio, ishaAngle)


        val lastIsha = double2Calendar(times[0] + adjustments.isha / 60).apply {
            add(Calendar.DATE, -1)
        }
        val fajr = double2Calendar(times[1] + adjustments.fajr / 60)
        val sunrise = double2Calendar(times[2] + adjustments.sunrise / 60)
        val dhuhr = double2Calendar(times[3] + 65 / 60 / 60 + adjustments.dhuhr / 60)
        val asr = double2Calendar(times[4] + adjustments.asr / 60)
        val maghrib = double2Calendar(times[5] + 2 / 60 + adjustments.sunset / 60)
        val isha = double2Calendar(times[6] + adjustments.isha / 60)
        val nextFajr = double2Calendar(times[7] + adjustments.fajr / 60).apply {
            add(Calendar.DATE, 1)
        }

        return arrayListOf(lastIsha, fajr, sunrise, dhuhr, asr, maghrib, isha, nextFajr)

    }

    private fun double2Calendar(hoursInDouble: Double): Calendar {

        val h = hoursInDouble.toInt()
        val m = ((hoursInDouble - h) * 60).toInt()
        val s = ((hoursInDouble - h - m / 60) * 3600).toInt()

        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, h)
            set(Calendar.MINUTE, m)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (s >= 30) {
                add(Calendar.MINUTE, 1)
            }

        }
    }


}