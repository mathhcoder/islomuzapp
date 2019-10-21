package uz.islom.model.app

import uz.islom.model.enums.SalatType

data class Adjustments(
        val fajr: Long,
        val sunrise: Long,
        val ishroq: Long,
        val dhuhr: Long,
        val asr: Long,
        val sunset: Long,
        val isha: Long,
        val qiyam: Long) {


    fun getAdjustment(salatType: SalatType): Long {
        return when (salatType) {
            SalatType.LAST_ISHA -> isha
            SalatType.FAJR -> fajr
            SalatType.SUNRISE -> sunrise
            SalatType.DHUHR -> dhuhr
            SalatType.ASR -> asr
            SalatType.MAGHRIB -> sunrise
            SalatType.ISHA -> isha
            SalatType.NEXT_FAJR -> fajr
        }
    }

}