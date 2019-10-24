package uz.islom.update

import uz.islom.model.app.Salat
import uz.islom.model.db.User
import uz.islom.ui.util.AppTheme
import java.util.*
import kotlin.collections.ArrayList

sealed class UpdatePath<T> {

    open fun key(): String? = null

    object SalatUpdate : UpdatePath<ArrayList<Salat>>() {
        override fun key(): String? = "dailySalatTimes"
    }

    object UserUpdate : UpdatePath<User>() {
        override fun key(): String? = "users"
    }

    object DateUpdate : UpdatePath<Calendar>() {
        override fun key(): String? = "date"
    }

    object ThemeUpdate : UpdatePath<AppTheme>() {
        override fun key(): String? = "theme"
    }

    object Resources : UpdatePath<android.content.res.Resources>() {
        override fun key(): String? = "resources"
    }
}