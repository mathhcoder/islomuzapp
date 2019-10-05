package uz.islom.model.app

data class Adjustments(
        val fajr: Double,
        val sunrise: Double,
        val ishroq: Double,
        val dhuhr: Double,
        val asr: Double,
        val sunset: Double,
        val isha: Double,
        val qiyam: Double) {


    fun getAdjustment(salatType: SalatType): Double {
        return when (salatType) {
            SalatType.LAST_QIYAM -> qiyam
            SalatType.LAST_ISHA -> isha
            SalatType.FAJR -> fajr
            SalatType.SUNRISE -> sunrise
            SalatType.ISHROQ -> ishroq
            SalatType.DHUHR -> dhuhr
            SalatType.ASR -> asr
            SalatType.MAGHRIB -> sunrise
            SalatType.ISHA -> isha
            SalatType.QIYAM -> qiyam
            SalatType.NEXT_FAJR -> fajr
        }
    }

}