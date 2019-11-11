package uz.islom.model.api

import io.reactivex.Single
import retrofit2.http.GET
import uz.islom.model.entity.User

interface UserApi {

    @GET("user")
    fun getUser(): Single<User>

}