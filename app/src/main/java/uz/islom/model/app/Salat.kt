package uz.islom.model.app

data class Salat(val id: Int,
                 val type: SalatType,
                 val time: Long,
                 val isRead: Boolean,
                 val notificationDiff: Long,
                 val notificationType: NotificationType?)