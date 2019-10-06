package uz.islom.model.app

data class PrayTimeState(
        val currentSalatType: SalatType,
        val nextSalatType: SalatType,
        val dailySalatTimes: DailySalatTimes
) {

    companion object {
        fun with(time: Long, dailySalatTimes: DailySalatTimes): PrayTimeState {

            val currentSalatType = when {
                time < dailySalatTimes.fajr -> SalatType.LAST_ISHA
                time < dailySalatTimes.sunrise -> SalatType.FAJR
                time < dailySalatTimes.dhuhr -> SalatType.SUNRISE
                time < dailySalatTimes.asr -> SalatType.DHUHR
                time < dailySalatTimes.maghrib -> SalatType.ASR
                time < dailySalatTimes.isha -> SalatType.MAGHRIB
                else -> SalatType.ISHA
            }

            val nextSalatType = when {
                time >= dailySalatTimes.isha -> SalatType.NEXT_FAJR
                time >= dailySalatTimes.maghrib -> SalatType.ISHA
                time >= dailySalatTimes.asr -> SalatType.MAGHRIB
                time >= dailySalatTimes.dhuhr -> SalatType.ASR
                time >= dailySalatTimes.sunrise -> SalatType.DHUHR
                time >= dailySalatTimes.fajr -> SalatType.SUNRISE
                else -> SalatType.FAJR
            }

            return PrayTimeState(currentSalatType, nextSalatType, dailySalatTimes)
        }
    }

}
