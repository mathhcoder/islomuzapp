package uz.islom.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class RadioService : Service() {

    override fun onBind(intent: Intent?): IBinder = RadioBinder()


    inner class RadioBinder : Binder() {
        val service: RadioService get() = this@RadioService
    }

    interface RadioListener {
        fun onStateChanged(state: Int)
        fun onMetaChanged(title: String?)
    }


}