package uz.islom.model.dm

import org.joda.time.DateTime
import uz.islom.fiqh.grigorianToHijr
import java.util.*

data class DateState(
        val hijri: DateTime,
        val grigorian: DateTime) {

    companion object {

        fun with(calendar: Calendar, adjustment: Int): DateState {

            val grigorian = DateTime(calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH] + 1,
                    calendar[Calendar.DATE],
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE],
                    calendar[Calendar.SECOND],
                    calendar[Calendar.MILLISECOND])

            val hijri = grigorianToHijr(calendar, adjustment)

            return DateState(hijri, grigorian)
        }
    }
}