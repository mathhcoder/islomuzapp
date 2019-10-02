package uz.islom.fiqh

import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import java.util.*
import kotlin.math.*


fun calculateSalatTimes(year: Int,
                        month: Int,
                        day: Int,
                        latitude: Double,
                        longitude: Double,
                        altitude: Double,
                        timeZoneDifference: Double,
                        fajrAngle: Double,
                        asrShadowRatio: Double,
                        ishaAngle: Double) {

    val jd = gregorian2Julian(year, month + 1, day)
    val d = jd - 2451545.0
    val g = fixAngle(357.529 + 0.98560028 * d)
    val q = fixAngle(280.459 + 0.98564736 * d)
    val l = fixAngle(q + 1.915 * sin(toRadians(g)) + 0.020 * sin(toRadians(2 * g)))
    val e = 23.439 - 0.00000036 * d
    val ra = fixHour(toDegrees(atan2(cos(toRadians(e)) * sin(toRadians(l)), cos(toRadians(l)))) / 15)
    val D = toDegrees(asin(sin(toRadians(e)) * sin(toRadians(l))))
    val eqt = q / 15 - ra


}

fun calculateSalatTimes(date: Calendar,
                        latitude: Double,
                        longitude: Double,
                        altitude: Double,
                        timeZoneDifference: Double,
                        fajrAngle: Double,
                        asrShadowRatio: Double,
                        ishaMinutesAfterMagrib: Int) {

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

private fun fixHour(h: Double): Double {
    var a = h
    a -= 24.0 * floor(a / 24.0)
    a = if (a < 0) a + 24 else a
    return a
}
