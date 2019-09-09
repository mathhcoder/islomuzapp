package uz.islom.model

import uz.islom.R

/**
 * developer -> Qodiriy
 * project -> IslomUz
 * date -> 09 May 2019
 * github -> github.com/qodiriy
 */

enum class Function(val id: Int, val nameRes: Int, val imageRes: Int) {
    KURAN(0, R.string.kuran, R.drawable.ic_quran),
    KIBLA(0, R.string.kibla, R.drawable.ic_kibla),
    MOSQUE(0, R.string.mosque, R.drawable.ic_mosque),
    RADIO(0, R.string.radio, R.drawable.ic_radio),
    CALENDAR(0, R.string.calendar, R.drawable.ic_calendar),
    MEDIA(0, R.string.media, R.drawable.ic_media),
    DUA(0, R.string.dua, R.drawable.ic_dua),
    ASMAUL_HUSNA(0, R.string.asmaul_husna, R.drawable.ic_asmaul_husna),
    TASBIH(0, R.string.tasbih, R.drawable.ic_tasbih),
    ZAKAT_CALCULATOR(0, R.string.zakat_calculator, R.drawable.ic_zakat_calculator),
}