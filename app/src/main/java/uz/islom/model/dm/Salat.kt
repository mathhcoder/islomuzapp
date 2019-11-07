package uz.islom.model.dm

import uz.islom.model.enums.NotificationType
import uz.islom.model.enums.SalatType
import java.io.Serializable
import java.util.*

data class Salat(val id: Int,
                 val type: SalatType,
                 val date: Calendar,
                 val isRead: Boolean,
                 val notificationDiff: Long,
                 val notificationType: NotificationType) : Serializable