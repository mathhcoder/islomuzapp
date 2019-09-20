package uz.islom.model

import uz.islom.R

enum class Function(val type: Int, val nameRes: Int, val imageRes: Int) {
    NOTIFICATION(0, R.string.notification, R.drawable.ic_notification),
    KURAN(0, R.string.kuran, R.drawable.ic_quran),
    MOSQUE(0, R.string.mosque, R.drawable.ic_mosque),
    KIBLA(0, R.string.kibla, R.drawable.ic_kibla),
    RADIO(0, R.string.radio, R.drawable.ic_radio),
    CALENDAR(0, R.string.calendar, R.drawable.ic_calendar),
    MEDIA(0, R.string.media, R.drawable.ic_media),
    DUA(0, R.string.dua, R.drawable.ic_dua),
    ASMAUL_HUSNA(0, R.string.asmaul_husna, R.drawable.ic_asmaul_husna),
    TASBIH(0, R.string.tasbih, R.drawable.ic_tasbih),
    ZAKAT_CALCULATOR(0, R.string.zakat_calculator, R.drawable.ic_zakat_calculator),
    FAVOURITE(0, R.string.favourite, R.drawable.ic_favourite),

    SETTINGS(1, R.string.settings, R.drawable.ic_settings),
    FEEDBACK(1, R.string.feedback, R.drawable.ic_email),
    ABOUT(1, R.string.about, R.drawable.ic_information),
    OFFER(1, R.string.offer, R.drawable.ic_offer),
    SHARE(1, R.string.share, R.drawable.ic_share),
    LOGOUT(1, R.string.logout, R.drawable.ic_logout),
}