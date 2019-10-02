package uz.islom.update

import uz.islom.model.db.User

sealed class UpdatePath<T> {

    open fun key(): String? = null

    class Users : UpdatePath<User>() {
        override fun key(): String? = "users"
    }

    class Resources() : UpdatePath<android.content.res.Resources>() {
        override fun key(): String? = "resources"
    }
}