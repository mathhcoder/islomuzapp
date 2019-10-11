package uz.islom.model.app

data class PrayTimeState(
        val currentSalatType: SalatType,
        val nextSalatType: SalatType,
        val salats: ArrayList<Salat>
) {

    companion object {
        fun with(time: Long, salats: ArrayList<Salat>): PrayTimeState {

            val nextSalatType = salats.sortedBy { it.time }.find { it.time > time }?.type
                    ?: SalatType.NEXT_FAJR

            val currentSalatType = salats.sortedByDescending { it.time }.find { it.time <= time }?.type
                    ?: SalatType.LAST_ISHA

            return PrayTimeState(currentSalatType, nextSalatType, salats)
        }
    }

}
