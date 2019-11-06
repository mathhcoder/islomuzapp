package uz.islom.model.enums

import android.graphics.Color

enum class ThemeType(
        val primaryColor: Int,//primary color, icon,
        val secondaryColor: Int, // secondary color, textcolor on primary color
        val tertiaryColor: Int, // textcolor on secondary color
        val toolBarColor: Int,
        val mainGradientStartColor: Int,
        val mainGradientMiddleColor: Int,
        val mainGradientEndColor: Int,
        val navigationIconsColor: Int,
        val mainIconsDarkColor: Int,
        val mainLineColor: Int,
        val mainFunctionsColor: Int,
        val mainSeekBarDotColor: Int,
        val mainSeekBarProgressColor: Int,
        val mainSeekBarFillColor: Int,
        val mainListColor: Int,
        val footerColor : Int
) {

    GREEN(
            Color.parseColor("#40803A"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#000000"),
            Color.parseColor("#123B15"),
            Color.parseColor("#0D4704"),
            Color.parseColor("#0B660E"),
            Color.parseColor("#39B050"),
            Color.parseColor("#5F6B7F"),
            Color.parseColor("#6A9E65"),
            Color.parseColor("#457544"),
            Color.parseColor("#40803A"),
            Color.parseColor("#DEF28E"),
            Color.parseColor("#DEF28E"),
            Color.parseColor("#4A9642"),
            Color.WHITE,
            Color.parseColor("#EBEDF5")
    ),


    DARK(
            Color.parseColor("#121212"),
            Color.parseColor("#01060001"),
            Color.parseColor("#01060001"),
            Color.parseColor("#0E110F"),
            Color.parseColor("#303030"),
            Color.parseColor("#303030"),
            Color.parseColor("#0D0D0D"),
            Color.parseColor("#7DBFFA"),
            Color.parseColor("#263035"),
            Color.parseColor("#465460"),
            Color.parseColor("#498CC7"),
            Color.parseColor("#465460"),
            Color.parseColor("#7DBFFA"),
            Color.parseColor("#7DBFFA"),
            Color.parseColor("#242423"),
            Color.parseColor("#EBEDF5")
    )

}

