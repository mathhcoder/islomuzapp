package uz.islom.model.dm

sealed class SignInMethod(val type: Int) {

    class Username(val userName: String, val password: String) : SignInMethod(0)

    class Google(val uid: String) : SignInMethod(1)

    class Facebook() : SignInMethod(2)

}