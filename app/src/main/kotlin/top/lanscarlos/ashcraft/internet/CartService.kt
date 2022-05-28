package top.lanscarlos.ashcraft.internet

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import top.lanscarlos.ashcraft.remote.RemoteCart

interface CartService {

    @FormUrlEncoded
    @POST("/cart/instance")
    fun getCart(@Field("userId") userId: Int): Call<RemoteCart>

}