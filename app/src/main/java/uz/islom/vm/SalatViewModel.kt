package uz.islom.vm

import com.google.android.gms.maps.model.LatLng
import timber.log.Timber
import uz.islom.fiqh.calculateSalatTimes
import uz.islom.model.app.*
import uz.islom.model.db.Content
import uz.islom.model.db.University
import uz.islom.update.UpdateCenter
import uz.islom.update.UpdatePath
import uz.islom.util.getUserToken
import java.util.*
import kotlin.collections.ArrayList

class SalatViewModel : BaseViewModel() {

    fun getCurrentSalatTimes(): ArrayList<Salat> {
        val salats = getSalatTimes(Date())
        UpdateCenter.post(salats, UpdatePath.SalatTimes())
        return salats
    }

    fun getSalatTimes(date: Date): ArrayList<Salat> {

        val latLng = LatLng(41.2825125, 69.1392815)
        val university = University(0, Content(0, "afs", "", "", ""), UniversityType.GENERAL.ordinal, 15.0, 15.0, 15.0)
        val madhab = Madhab.HANAFI
        val adjustments = Adjustments(0, 0, 0, 0, 0, 0, 0, 0)

        val salatTimes = calculatePrayTimes(date, latLng, university, madhab, adjustments)

        val fajr = Salat(0, SalatType.FAJR, salatTimes[0], false, 0, null)
        val sunrise = Salat(0, SalatType.SUNRISE, salatTimes[1], false, 0, null)
        val dhuhr = Salat(0, SalatType.DHUHR, salatTimes[2], false, 0, null)
        val asr = Salat(0, SalatType.ASR, salatTimes[3], false, 0, null)
        val maghrib = Salat(0, SalatType.MAGHRIB, salatTimes[4], false, 0, null)
        val isha = Salat(0, SalatType.ISHA, salatTimes[5], false, 0, null)

        return arrayListOf(fajr, sunrise, dhuhr, asr, maghrib, isha)
    }

    private fun calculatePrayTimes(date: Date, latLng: LatLng, university: University, madhab: Madhab, adjustments: Adjustments): List<Long> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date.time

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val fajrAngle = university.fajr
        val ishaAngle = university.isha
        val asrShadowRatio = if (madhab == Madhab.HANAFI) 2.0 else 1.0

        val times = calculateSalatTimes(year, month, day, latLng.latitude,
                latLng.longitude, .0, fajrAngle, asrShadowRatio, ishaAngle)

        val fajr = (times[0] * 60 * 60 * 1000).toLong() + adjustments.fajr
        val sunrise = (times[1] * 60 * 60 * 1000) + adjustments.sunrise
        val dhuhr = (times[2] * 60 * 60 * 1000).toLong() + 65 * 1000 + adjustments.dhuhr // adding 65 seconds
        val asr = (times[3] * 60 * 60 * 1000).toLong() + adjustments.asr
        val maghrib = (times[4] * 60 * 60 * 1000).toLong() + 2 * 60 * 1000 + adjustments.sunset  // (adding 2 minutes)
        val isha = (times[5] * 60 * 60 * 1000).toLong() + adjustments.isha

        val start = date.time - date.time % 86400000

        return arrayListOf(start + fajr, start + sunrise.toLong(), start + dhuhr, start + asr, start + maghrib, start + isha)

    }
}