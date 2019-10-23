package uz.islom.ext

import android.content.Context
import org.joda.time.DateTime
import uz.islom.R
import uz.islom.android.string
import java.util.*

fun Long.milliseconds2formattedTime(): String {
    val d = this / 86400000
    val h = (this - d * 86400000) / 3600000
    val m = (this - d * 86400000 - h * 3600000) / 60000
    val s = (this - d * 86400000 - h * 3600000 - m * 60000) / 1000

    val seconds = if (s >= 10) "$s" else "0$s"
    val minutes = if (m >= 10) "$m" else "0$m"
    val hours = if (h >= 10) "$h" else "0$h"
    val days = if (d >= 10) "$d" else "0$d"

    var result = "$minutes:$seconds"
    // if (h > 0) {
    result = "$hours:$result"
    // }
    if (d > 0) {
        result = "$days:$result"
    }

    return result

}


fun Context.calendar2uzbekTimeFormat(dateTime: DateTime): String {
    val weekDay = resources.getStringArray(R.array.week_days_gregorian).getOrNull(dateTime.dayOfWeek().get())?.substring(0, 3)
    val dayOfMonth = dateTime.dayOfMonth().get()
    val monthOfYear = resources.getStringArray(R.array.year_months_gregorian).getOrNull(dateTime.monthOfYear().get()-1)
    val year = dateTime.year().get()

    return string(R.string.grigorian_date_format)?.let {
        String.format(it, weekDay, dayOfMonth, monthOfYear, year)
    } ?: "format not found"

}

fun Context.calendar2uzbekHijrTimeFormat(dateTime: DateTime): String {

    val dayOfMonth = dateTime.dayOfMonth().get()
    val monthOfYear = resources.getStringArray(R.array.year_months_hijri).getOrNull(dateTime.monthOfYear().get()-1)
    val year = dateTime.year().get()

    return string(R.string.hijri_date_format)?.let {
        String.format(it, dayOfMonth, monthOfYear, year)
    } ?: "format not found"

}