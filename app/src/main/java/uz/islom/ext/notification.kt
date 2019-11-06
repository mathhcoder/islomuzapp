package uz.islom.ext

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


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


fun makeAdhanChannel(soundUri: Uri){

}