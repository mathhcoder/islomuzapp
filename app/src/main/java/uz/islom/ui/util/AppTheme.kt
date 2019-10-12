package uz.islom.ui.util

import android.graphics.Color

enum class AppTheme(
        val primaryColor: Int,//primary color, icon,
        val secondaryColor: Int, // secondary color, textcolor on primary color
        val tertiaryColor: Int, // textcolor on secondary color
        val toolBarColor: Int,
        val mainGradientStartColor: Int,
        val mainGradientEndColor: Int,
        val mainIconsLightColor: Int,
        val mainIconsDarkColor: Int,
        val mainLineColor: Int,
        val mainFunctionsColor: Int,
        val mainSeekBarColor: Int,
        val mainSeekBarProgressColor: Int,
        val mainListColor: Int
) {

    GREEN(
            Color.parseColor("#40803A"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#000000"),
            Color.parseColor("#123B15"),
            Color.parseColor("#3A8549"),
            Color.parseColor("#103B09"),
            Color.parseColor("#DEF28E"),
            Color.parseColor("#6A9E65"),
            Color.parseColor("#457544"),
            Color.parseColor("#40803A"),
            Color.parseColor("#497951"),
            Color.parseColor("#E8F695"),
            Color.WHITE
    ),


    DARK(
            Color.parseColor("#121212"),
            Color.parseColor("#01060001"),
            Color.parseColor("#01060001"),
            Color.parseColor("#0E110F"),
            Color.parseColor("#303030"),
            Color.parseColor("#0D0D0D"),
            Color.parseColor("#7DBFFA"),
            Color.parseColor("#263035"),
            Color.parseColor("#465460"),
            Color.parseColor("#498CC7"),
            Color.parseColor("#465460"),
            Color.parseColor("#7DBFFA"),
            Color.parseColor("#242423")
    )

}

