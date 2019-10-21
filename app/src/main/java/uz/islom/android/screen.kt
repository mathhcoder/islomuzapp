package uz.islom.android

import android.content.Context


fun Context.screenWidth(): Int {
    return resources.displayMetrics.widthPixels
}