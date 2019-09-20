package uz.islom.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import uz.islom.ui.base.BaseTextView

const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT
const val full = ViewGroup.LayoutParams.MATCH_PARENT


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

fun TextView.setTextSizeSp(value: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, value.toFloat())
}

