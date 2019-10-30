package uz.islom.fiqh

import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

const val makkahLat = 21.42250833
const val makkahLng = 39.82616111

fun calculateQibla(lat: Double, lng : Double): Double {
    val deg = toDegrees(atan2(sin(toRadians(makkahLng - lng)), cos(toRadians(lat)) * tan(toRadians(makkahLat)) - sin(toRadians(lat)) * cos(toRadians(makkahLng - lng))))
    return if (deg >= 0) deg else deg + 360
}
