package uz.islom.model.app

data class DailySalatTimes(
        val fajr: Long, val sunrise: Long, val dhuhr: Long,
        val asr: Long, val maghrib: Long, val isha: Long)
