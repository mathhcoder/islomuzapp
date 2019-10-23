package uz.islom.fiqh

import org.joda.time.DateTime
import org.joda.time.chrono.IslamicChronology
import java.util.*


fun grigorianToHijr(calendar: Calendar, adjustment: Int): DateTime {

    val adjustedTime = calendar.apply {
        add(Calendar.DATE, adjustment)
    }

    val dtISO = DateTime(adjustedTime[Calendar.YEAR],
            adjustedTime[Calendar.MONTH]+1,
            adjustedTime[Calendar.DATE],
            adjustedTime[Calendar.HOUR_OF_DAY],
            adjustedTime[Calendar.MINUTE],
            adjustedTime[Calendar.SECOND],
            adjustedTime[Calendar.MILLISECOND])

    return dtISO.withChronology(IslamicChronology.getInstance())

}