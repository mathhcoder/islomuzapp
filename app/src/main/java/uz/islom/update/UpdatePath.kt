package uz.islom.update

import uz.islom.model.db.User

sealed class UpdatePath<T> {

    open fun key(): String? = null

    class SalatTimes : UpdatePath<uz.islom.model.app.DailySalatTimes>() {
        override fun key(): String? = "dailySalatTimes"
    }

    object Users : UpdatePath<User>() {
        override fun key(): String? = "users"
    }

    object Resources : UpdatePath<android.content.res.Resources>() {
        override fun key(): String? = "resources"
    }
}