package top.lanscarlos.ashcraft.internet

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST("/login")
    fun login(@Field("phone") phone: String, @Field("password") password: String): Call<ResponseBody>

}