package uz.islom.io.preference

import android.content.Context
import androidx.preference.PreferenceManager
import uz.islom.model.app.SalatType

fun getAdjustments(context: Context, salatType: SalatType): Int {
    return PreferenceManager.getDefaultSharedPreferences(context).getInt("salat$salatType", 0)
}

