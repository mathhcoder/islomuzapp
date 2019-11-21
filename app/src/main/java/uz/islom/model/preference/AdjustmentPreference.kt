package uz.islom.model.preference

import android.content.SharedPreferences
import uz.islom.model.dm.Adjustments
import uz.islom.model.enums.SalatType

class AdjustmentPreference(val preference: SharedPreferences) {

    var adjustments: Adjustments
        get() {
            return Adjustments(
                    getAdjustments(SalatType.FAJR),
                    getAdjustments(SalatType.SUNRISE),
                    getAdjustments(SalatType.SUNRISE),
                    getAdjustments(SalatType.DHUHR),
                    getAdjustments(SalatType.ASR),
                    getAdjustments(SalatType.MAGHRIB),
                    getAdjustments(SalatType.ISHA),
                    getAdjustments(SalatType.ISHA)
            )
        }
        set(value) {
            setAdjustment(SalatType.FAJR, value.fajr)
            setAdjustment(SalatType.SUNRISE, value.sunrise)
            setAdjustment(SalatType.DHUHR, value.dhuhr)
            setAdjustment(SalatType.ASR, value.asr)
            setAdjustment(SalatType.MAGHRIB, value.sunset)
            setAdjustment(SalatType.ISHA, value.isha)
        }

    fun getAdjustments(salatType: SalatType): Long {
        return preference.getLong(salatType.name, 0L)
    }

    fun setAdjustment(salatType: SalatType, adjustment: Long) {
        preference.edit().putLong(salatType.name, adjustment).apply()
    }

}



