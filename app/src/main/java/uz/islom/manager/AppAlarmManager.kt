package uz.islom.manager

import uz.islom.IslomUzApp
import uz.islom.manager.alarm.AdhanAlarm
import uz.islom.model.repository.SalatRepository

object AppAlarmManager {

    fun getAdhan(): AdhanAlarm {
        val adjustmentPreference = IslomUzApp.getInstance().preferenceManager.getAdjustmentPreference()
        val userPreference = IslomUzApp.getInstance().preferenceManager.getUserPreference()
        val salatRepository = SalatRepository(userPreference, adjustmentPreference)
        return AdhanAlarm(salatRepository)
    }
}