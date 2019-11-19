package uz.islom.model.enums

import uz.islom.R

enum class FunctionType(val nameRes: Int, val imageRes: Int) {
    QURAN(R.string.quran, R.drawable.ic_quran),
    MOSQUE(R.string.mosque, R.drawable.ic_mosque),
    QIBLA(R.string.qibla, R.drawable.ic_kibla),
    RADIO(R.string.radio, R.drawable.ic_radio),
    CALENDAR(R.string.calendar, R.drawable.ic_calendar),
    MEDIA(R.string.media, R.drawable.ic_media),
    DUA(R.string.dua, R.drawable.ic_dua),
    ASMAUL_HUSNA(R.string.names, R.drawable.ic_asmaul_husna),
    ZIKR(R.string.tasbih, R.drawable.ic_tasbih),
    ZAKAT_CALCULATOR(R.string.zakat_calculator, R.drawable.ic_zakat_calculator),
}