package uz.islom.model.app

import androidx.annotation.StringRes
import uz.islom.R

enum class SalatType(@StringRes val title: Int) {
    LAST_ISHA(R.string.isha),
    FAJR(R.string.fajr),
    SUNRISE(R.string.sunrise),
    DHUHR(R.string.dhuhr),
    ASR(R.string.asr),
    MAGHRIB(R.string.maghrib),
    ISHA(R.string.isha),
    NEXT_FAJR(R.string.fajr)
}