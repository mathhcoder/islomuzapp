package uz.islom.vm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import uz.islom.model.app.Salat
import uz.islom.service.CommandListener
import uz.islom.ui.MainActivity

class AdhanViewModel : BaseViewModel() {

    fun makeNewAdhanAlarms(context: Context, salat: Salat) {

        val operation = Intent(context, CommandListener::class.java)
        operation.action = "${context.packageName}.adhan.start"
        operation.putExtra("salat", salat)

        val show = Intent(context, MainActivity::class.java)

        val showIntent = PendingIntent.getActivity(context, 1, show, PendingIntent.FLAG_UPDATE_CURRENT)

        val operationIntent = PendingIntent.getBroadcast(context, 1, operation, PendingIntent.FLAG_UPDATE_CURRENT)

        (context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.let {
            AlarmManagerCompat.setAlarmClock(it, salat.date.timeInMillis, showIntent, operationIntent)
        }
    }

}