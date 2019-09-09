package uz.islom.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


/**
 * developer -> Qodiriy
 * project -> App
 * date -> 09 May 2019
 * github -> github.com/qodiriy
 */

fun Activity.hideKeyboard() {
    var view = currentFocus
    if (view == null) view = View(this)

    (getSystemService(Activity.INPUT_METHOD_SERVICE)
            as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.dp(value: Int): Int {
    return resources.dp(value)
}

fun Context.dp(value: Int): Int {
    return resources.dp(value)
}

fun Fragment.dp(value: Int): Int {
    return resources.dp(value)
}

fun Resources.dp(value: Int): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            displayMetrics
    ).toInt()
}

