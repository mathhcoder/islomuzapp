package uz.islom.util

import android.content.Context
import android.os.Vibrator
import androidx.fragment.app.Fragment

fun Fragment.zing() {
    (context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).run {
        vibrate(200)
    }
}