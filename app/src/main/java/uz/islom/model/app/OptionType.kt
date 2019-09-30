package uz.islom.model.app

import uz.islom.R

enum class OptionType(val nameRes: Int, val imageRes: Int) {
    SETTINGS(R.string.settings, R.drawable.ic_settings),
    FEEDBACK(R.string.feedback, R.drawable.ic_email),
    ABOUT(R.string.about, R.drawable.ic_information),
    OFFER(R.string.offer, R.drawable.ic_offer),
    SHARE(R.string.share, R.drawable.ic_share),
    LOGOUT(R.string.logout, R.drawable.ic_logout),
}