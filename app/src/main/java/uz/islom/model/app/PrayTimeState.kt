package uz.islom.model.app

import uz.islom.model.app.SalatType

data class PrayTimeState(
        val currentSalatType : SalatType,
        val nextSalatType : SalatType
)