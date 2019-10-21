package uz.islom.ext

fun milliseconds2formattedTime(time: Long): String {
    val d = time / 86400000
    val h = (time - d * 86400000) / 3600000
    val m = (time - d * 86400000 - h * 3600000) / 60000
    val s = (time - d * 86400000 - h * 3600000 - m*60000) / 1000

    val seconds = if (s >= 10) "$s" else "0$s"
    val minutes = if (m >= 10) "$m" else "0$m"
    val hours = if (h >= 10) "$h" else "0$h"
    val days = if (d >= 10) "$d" else "0$d"

    var result = "$minutes:$seconds"
    if (h > 0) {
        result = "$hours:$result"
    }
    if (d > 0) {
        result = "$days:$result"
    }

    return result

}