package uz.islom.vm

import com.google.android.gms.maps.model.LatLng
import timber.log.Timber
import uz.islom.fiqh.calculateSalatTimes
import uz.islom.model.app.Adjustments
import uz.islom.model.app.Madhab
import uz.islom.model.app.DailySalatTimes
import uz.islom.model.app.UniversityType
import uz.islom.model.db.Content
import uz.islom.model.db.University
import uz.islom.update.UpdateCenter
import uz.islom.update.UpdatePath
import java.util.*

class SalatTimeViewModel : BaseViewModel() {

    private var dailySalatTimes: DailySalatTimes

    init {
        dailySalatTimes = calculatePrayTimes(Date(), LatLng(41.2825125,69.1392815), University(0, Content(0, "afs", "", "", ""), UniversityType.GENERAL.ordinal, 15.0, 15.0, 15.0), Madhab.HANAFI, Adjustments(.0, .0, .0, .0, .0, .0, .0, .0))
    }

    fun getSalatTimes() = dailySalatTimes

    private fun calculatePrayTimes(date: Date, latLng: LatLng, university: University, madhab: Madhab, adjustments: Adjustments): DailySalatTimes {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date.time

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val fajrAngle = university.fajr
        val asrShadowRatio = if (madhab == Madhab.HANAFI) 2.0 else 1.0

        val times = calculateSalatTimes(year, month, day, latLng.latitude, latLng.longitude, .0,
                 fajrAngle, asrShadowRatio, asrShadowRatio)

        val fajr = (times[0] + adjustments.fajr) * 60 * 60 * 1000
        val sunrise = (times[1] + adjustments.sunrise) * 60 * 60 * 1000
        val dhuhr = (times[2] + 65 / 60 / 60 + adjustments.dhuhr) * 60 * 60 * 1000  // (adding 65 seconds)
        val asr = (times[3] + adjustments.asr) * 60 * 60 * 1000
        val maghrib = (times[4] + 2 / 60 + adjustments.sunset) * 60 * 60 * 1000 // (adding 2 minutes)
        val isha = (times[5] + adjustments.isha) * 60 * 60 * 1000

        Timber.d("f:$fajr s:$sunrise d:$dhuhr a:$asr m:$maghrib i:$isha")

        val start = date.time - date.time % 86400000

        dailySalatTimes = DailySalatTimes(start + fajr.toLong(), start + sunrise.toLong(), start + dhuhr.toLong(), start + asr.toLong(), start + maghrib.toLong(), start + isha.toLong())

        UpdateCenter.post(dailySalatTimes, UpdatePath.SalatTimes())

        return dailySalatTimes
    }
}