package uz.islom.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CommandListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        when (intent?.action) {

            "${context.packageName}.salat.adhan" -> {

            }

            "${context.packageName}.salat.notify" -> {

            }

        }
    }

}