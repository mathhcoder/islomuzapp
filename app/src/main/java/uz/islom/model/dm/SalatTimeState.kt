package uz.islom.model.dm

import uz.islom.model.entity.Salat

data class SalatTimeState(
        val salats: ArrayList<Salat>,
        val currentSalat: Salat,
        val nextSalat: Salat,
        val left: Long,
        val progress: Float) {

    companion object {

        fun with(salats: ArrayList<Salat>): SalatTimeState {

            val currentTime = System.currentTimeMillis()

            val nextSalat = salats.sortedBy { it.date }.find { it.date.timeInMillis > currentTime }
                    ?: salats.maxBy { it.date }!!

            val currentSalat = salats.sortedByDescending { it.date }.find { it.date.timeInMillis <= currentTime }
                    ?: salats.minBy { it.date }!!

            val left = nextSalat.date.timeInMillis - currentTime

            val progress = (currentTime - currentSalat.date.timeInMillis).toFloat() / (nextSalat.date.timeInMillis - currentSalat.date.timeInMillis).toFloat()

            return SalatTimeState(salats, currentSalat, nextSalat, left, progress)
        }
    }
}