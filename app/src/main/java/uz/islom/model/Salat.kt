package uz.islom.model

import androidx.annotation.StringRes
import uz.islom.R

enum class Salat(@StringRes val title: Int) {
    LAST_ISHA(R.string.isha),
    LAST_QIYAM(R.string.isha),
    FAJR(R.string.fajr),
    SUNRISE(R.string.fajr),
    ISHROQ(R.string.fajr),
    DHUHR(R.string.dhuhr),
    ASR(R.string.asr),
    MAGHRIB(R.string.maghrib),
    ISHA(R.string.isha),
    QIYAM(R.string.isha),
    NEXT_FAJR(R.string.fajr)
}