package uz.islom.ext

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber


fun makeNotification(context: Context,
                     id: Int,
                     title: String,
                     text: String,
                     @DrawableRes icon: Int,
                     priority: Int,
                     autoCancel: Boolean,
                     chanelId: String,
                     activity: Class<*>
) {

    Timber.d("MakingNotification")
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, Intent(context, activity).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }, 0)

    val notification = NotificationCompat.Builder(context, chanelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(autoCancel)
            .build()

    with(NotificationManagerCompat.from(context)) {
        notify(id, notification)
    }
}


@TargetApi(Build.VERSION_CODES.O)
fun makeChannel(context: Context, chanelId: String) {
    val channel = NotificationChannel(chanelId, chanelId, NotificationManager.IMPORTANCE_HIGH).apply {
        enableLights(true)
        enableVibration(true)
       // setSound(sou, createAdhanAudioAttributes())
        vibrationPattern = longArrayOf(0, 1000, 500, 1000)
    }

    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
        createNotificationChannel(channel)
    }

}
