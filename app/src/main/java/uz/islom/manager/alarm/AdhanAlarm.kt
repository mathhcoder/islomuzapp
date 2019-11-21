package uz.islom.manager.alarm

import android.content.Intent
import android.os.Bundle
import timber.log.Timber
import uz.islom.ext.makeAlarm
import uz.islom.manager.service.CommandListener
import uz.islom.model.dm.Salat
import uz.islom.model.dm.SalatTimeState
import uz.islom.model.repository.SalatRepository
import java.text.SimpleDateFormat
import java.util.*

class AdhanAlarm(private val salatRepository: SalatRepository) : BaseAlarm() {

    fun setNextSalatAlarm() {
        val state = SalatTimeState.with(salatRepository.getSalatTimes(Calendar.getInstance()))
        setAlarm(state.nextSalat)
    }

    private fun setAlarm(salat: Salat) {

        Timber.i("Alarm sat for ${salat.type.title} time: ${SimpleDateFormat("dd:MM HH:mm", Locale.getDefault()).format(salat.date.time)}")

        val bundle = Bundle().apply {
            putSerializable("salat", salat)
        }

        val intent = Intent(context, CommandListener::class.java)
                .setAction("uz.islom.salat.adhan")
                .putExtra("extra", bundle)

        val time = salat.date.timeInMillis

        makeAlarm(context, intent, time)
    }
}