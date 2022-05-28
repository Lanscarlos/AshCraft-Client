package top.lanscarlos.ashcraft.internet

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import top.lanscarlos.ashcraft.remote.RemoteUser

interface UserService {

    @FormUrlEncoded
    @POST("/login")
    fun login(@Field("phone") phone: String, @Field("password") password: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("phone") phone: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("signature") signature: String,
        @Field("address") address: String,
        @Field("money") money: Double = 0.0,
        @Field("gender") gender: Int = 2,
    ): Call<RemoteUser>

}