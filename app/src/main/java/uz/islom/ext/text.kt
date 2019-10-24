package uz.islom.ext

import android.text.Spannable
import android.text.SpannableString
import uz.islom.ui.custom.TrimmedTextView

fun String.ellipsize(): SpannableString {
    val s = SpannableString(this)
    s.setSpan(TrimmedTextView.EllipsizeRange.ELLIPSIS_AT_END, 0, s.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return s
}