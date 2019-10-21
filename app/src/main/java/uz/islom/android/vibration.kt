package uz.islom.android

import android.content.Context
import android.os.Vibrator
import androidx.fragment.app.Fragment

fun Fragment.zing() {
    context?.zing()
}
//TODO use not deprecated methods
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