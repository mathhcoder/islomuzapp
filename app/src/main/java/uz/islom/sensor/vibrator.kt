package uz.islom.sensor

import android.content.Context
import android.os.Vibrator
import androidx.fragment.app.Fragment

fun Fragment.zing() {
    context?.zing()
}

fun Context.zing() {
    (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).run {
        vibrate(200)
    }
}

fun Context.vibrate() {
    (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).run {
        vibrate(200)
    }
}