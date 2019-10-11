package uz.islom.fiqh

import timber.log.Timber
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.*


fun calculateSalatTimes(year: Int,
                        month: Int,
                        day: Int,
                        latitude: Double,
                        longitude: Double,
                        altitude: Double,
                        fajrAngle: Double,
                        asrShadowRatio: Double,
                        ishaAngle: Double): Array<Double> {

    val jd = gregorian2Julian(year, month + 1, day)
    val january2000 = jd - 2451545.0
    val g = fixAngle(357.529 + 0.98560028 * january2000)
    val q = fixAngle(280.459 + 0.98564736 * january2000)
    val l = fixAngle(q + 1.915 * sin(toRadians(g)) + 0.020 * sin(toRadians(2 * g)))
    val e = fixHour(23.439 - 0.00000036 * january2000)
    val ra = fixHour(toDegrees(atan2(cos(toRadians(e)) * sin(toRadians(l)), cos(toRadians(l)))) / 15)
    val d = toDegrees(asin(sin(toRadians(e)) * sin(toRadians(l))))
    val eqt = q / 15 - ra


    val midday = fixHour(12 - longitude / 15 - eqt)
    val fajr = fixHour(midday - toDegrees(acos((-sin(toRadians(fajrAngle)) - sin(toRadians(latitude)) * sin(toRadians(d))) / (cos(toRadians(latitude)) * cos(toRadians(d))))) / 15)
    val sunrise = fixHour(midday - toDegrees(acos((-sin(toRadians(0.833)) - sin(toRadians(latitude)) * sin(toRadians(d))) / (cos(toRadians(latitude)) * cos(toRadians(d))))) / 15)
    val asr = fixHour(midday + toDegrees(acos((sin(atan(1 / (asrShadowRatio + tan(toRadians(latitude - d))))) - sin(toRadians(latitude)) * sin(toRadians(d))) / (cos(toRadians(latitude)) * cos(toRadians(d))))) / 15)
    val sunset = fixHour(midday + toDegrees(acos((-sin(toRadians(0.833)) - sin(toRadians(latitude)) * sin(toRadians(d))) / (cos(toRadians(latitude)) * cos(toRadians(d))))) / 15)
    val isha = fixHour(midday + toDegrees(acos((-sin(toRadians(ishaAngle)) - sin(toRadians(latitude)) * sin(toRadians(d))) / (cos(toRadians(latitude)) * cos(toRadians(d))))) / 15)

    Timber.d("fajr:$fajr sunrise:$sunrise dhuhr:$midday asr:$asr maghrib:$sunset isha:$isha")

    return arrayOf(fajr, sunrise, midday, asr, sunset, isha)

}


private fun gregorian2Julian(year: Int, month: Int, day: Int): Double {
    var y = year
    var m = month

    if (m <= 2) {
        y -= 1
        m += 12
    }
    val a = floor(y / 100.0)
    val b = 2 - a + floor(a / 4.0)

    return (floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b) - 1524.5
}

private fun fixAngle(a: Double): Double {
    var b = a
    b -= 360 * floor(b / 360.0)
    b = if (b < 0) b + 360 else b
    return b
}

fun fixHour(h: Double): Double {
    var a = h
    a -= 24.0 * floor(a / 24.0)
    a = if (a < 0) a + 24 else a
    return a
}
