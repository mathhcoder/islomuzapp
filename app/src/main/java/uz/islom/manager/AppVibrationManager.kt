package uz.islom.manager

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class AppVibrationManager(private val vibrator: Vibrator) {

    fun makeZikrSimpleVibration() {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(150,10))
        } else {
            vibrator.vibrate(150)
        }
    }

    fun makeZikrTypeChangedVibration() {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(300,20))
        } else {
            vibrator.vibrate(300)
        }
    }

}