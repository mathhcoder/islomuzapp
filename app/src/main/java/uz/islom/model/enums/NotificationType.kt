package uz.islom.model.enums

import uz.islom.R

enum class NotificationType(val id: Int, val image: Int) {
    NONE(0, R.drawable.ic_notification_off),
    SILENT(1, R.drawable.ic_notification_silent),
    DEFAULT(2, R.drawable.ic_notification_volume),
    ADAN(3, R.drawable.ic_notification_adhan)
}