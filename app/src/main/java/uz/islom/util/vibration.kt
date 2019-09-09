package uz.islom.util

import android.content.Context
import android.os.Vibrator
import androidx.fragment.app.Fragment

/**
 * developer -> Qodiriy
 * project -> App
 * date -> 21 May 2019
 * github -> github.com/qodiriy
 */

fun Fragment.zing() {
    (context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).run {
        vibrate(200)
    }
}