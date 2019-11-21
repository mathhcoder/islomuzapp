package uz.islom.manager.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber
import uz.islom.IslomUzApp
import uz.islom.model.dm.Salat

class CommandListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        when (intent?.action) {

            Intent.ACTION_REBOOT -> {
                IslomUzApp.getInstance().alarmManager.getAdhan().setNextSalatAlarm()
            }

            Intent.ACTION_TIME_CHANGED -> {
                IslomUzApp.getInstance().alarmManager.getAdhan().setNextSalatAlarm()
            }

            Intent.ACTION_DATE_CHANGED -> {
                IslomUzApp.getInstance().alarmManager.getAdhan().setNextSalatAlarm()
            }

            Intent.ACTION_TIMEZONE_CHANGED -> {
                IslomUzApp.getInstance().alarmManager.getAdhan().setNextSalatAlarm()
            }

            "uz.islom.salat.adhan" -> {

                Timber.d("Command Listener : On Adhan Alarm Received")

                (intent.getBundleExtra("extra")?.getSerializable("salat") as? Salat)?.let {
                    Timber.d("Command Listener: salat : $it")
                    IslomUzApp.getInstance().notificationManager.getAdhan().notifyAdhan(context.resources, it)
                }

            }

            "${context.packageName}.salat.reminder" -> {

            }

        }
    }

}