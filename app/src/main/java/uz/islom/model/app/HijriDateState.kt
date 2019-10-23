package uz.islom.model.app

import org.joda.time.DateTime
import uz.islom.fiqh.grigorianToHijr
import java.util.*

data class HijriDateState(
        val hijri: DateTime,
        val grigorian: DateTime) {

    companion object {

        fun with(calendar: Calendar, adjustment: Int): HijriDateState {

            val grigorian = DateTime(calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH] + 1,
                    calendar[Calendar.DATE],
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE],
                    calendar[Calendar.SECOND],
                    calendar[Calendar.MILLISECOND])

            val hijri = grigorianToHijr(calendar, adjustment)

            return HijriDateState(hijri, grigorian)
        }
    }
}