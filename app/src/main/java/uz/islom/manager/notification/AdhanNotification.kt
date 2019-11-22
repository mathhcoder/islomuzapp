package uz.islom.manager.notification

import android.content.Context
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import uz.islom.R
import uz.islom.ext.makeChannel
import uz.islom.ext.makeNotification
import uz.islom.ext.string
import uz.islom.model.entity.Salat
import uz.islom.model.enums.SalatType
import uz.islom.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class AdhanNotification(private val context: Context) : BaseNotification() {


    private val channelId by lazy {
        "${context.packageName}.adhan"
    }

    private val icon by lazy {
        R.mipmap.ic_launcher
    }

    private val priority by lazy {
        NotificationCompat.PRIORITY_MAX
    }


    fun notifyAdhan(resources: Resources, salat: Salat) {

        makeChannel(context, channelId)

        val id = salat.id
        val title = if (salat.type == SalatType.SUNRISE) {
            resources.string(R.string.notification_adhan_close_format)?.let {
                String.format(it, SalatType.FAJR.title,SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time))
            } ?: run {
                ""
            }
        } else {
            resources.string(R.string.notification_adhan_enter_format)?.let {
                String.format(it, salat.type.title,SimpleDateFormat("HH:mm", Locale.getDefault()).format(salat.date.time))
            } ?: run {
                ""
            }
        }

        val activity = MainActivity::class.java
        val autoCancel = false

        makeNotification(context, id, title, "", icon, priority, autoCancel, channelId, activity)
    }

}