package uz.islom.fiqh

import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


fun calculateQibla(lat: Double, lng : Double): Double {
    val lngA = 39.82616111
    val latA = 21.42250833
    val deg = toDegrees(atan2(sin(toRadians(lngA - lng)), cos(toRadians(lat)) * tan(toRadians(latA)) - sin(toRadians(lat)) * cos(toRadians(lngA - lng))))
    return if (deg >= 0) deg else deg + 360
}
