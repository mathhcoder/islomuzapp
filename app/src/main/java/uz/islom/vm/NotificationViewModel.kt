package uz.islom.vm

import android.content.Context
import androidx.core.app.NotificationCompat
import uz.islom.R
import uz.islom.model.app.Salat
import uz.islom.ui.MainActivity
import uz.islom.ext.makeNotification
import java.text.SimpleDateFormat
import java.util.*

class NotificationViewModel : BaseViewModel() {


    fun notifyAdhan(context: Context, salat: Salat) {

        val id = salat.id
        val title = "Toshkent vaqti bilan ${salat.type.title} soat ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time)} da kirdi"
        val icon = R.mipmap.ic_launcher
        val priority = NotificationCompat.PRIORITY_MAX
        val activity = MainActivity::class.java
        val autoCancel = false
        val channelId = "${context.packageName}.channel.adhan"

        makeNotification(context, id, title, "", icon, priority, autoCancel, channelId, activity)
    }

    fun notifyAdhanReminder(context: Context, salat: Salat) {
        val id = -salat.id
        val title = "Toshkent vaqti bilan ${salat.type.title} soat ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time)} da kirdi"
        val text = "Toshkent vaqti bilan ${salat.type.title} soat ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time)} da kirdi"
        val icon = R.mipmap.ic_launcher
        val priority = NotificationCompat.PRIORITY_MAX
        val activity = MainActivity::class.java
        val autoCancel = false
        val channelId = "${context.packageName}.channel.adhan.reminder"

        makeNotification(context, id, title, text, icon, priority, autoCancel, channelId, activity)

    }

}