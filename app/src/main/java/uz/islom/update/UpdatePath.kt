package uz.islom.update

import uz.islom.model.app.Salat
import uz.islom.model.db.User
import java.util.*
import kotlin.collections.ArrayList

sealed class UpdatePath<T> {

    open fun key(): String? = null

    class SalatTimes : UpdatePath<ArrayList<Salat>>() {
        override fun key(): String? = "dailySalatTimes"
    }

    object Users : UpdatePath<User>() {
        override fun key(): String? = "users"
    }

    class DateChanged : UpdatePath<Calendar>() {
        override fun key(): String? = "date"
    }

    object Resources : UpdatePath<android.content.res.Resources>() {
        override fun key(): String? = "resources"
    }
}