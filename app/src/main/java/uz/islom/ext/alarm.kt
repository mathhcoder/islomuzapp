package uz.islom.ext

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent

fun makeAlarm(context: Context, intent: Intent, time: Long) {
    val pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    (context.getSystemService(ALARM_SERVICE) as AlarmManager).set(AlarmManager.RTC_WAKEUP, time, pi)
}