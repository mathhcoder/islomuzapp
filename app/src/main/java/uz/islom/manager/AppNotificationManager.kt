package uz.islom.manager

import uz.islom.IslomUzApp
import uz.islom.manager.notification.AdhanNotification

object AppNotificationManager {

    fun getAdhan() = AdhanNotification(IslomUzApp.getInstance())

}