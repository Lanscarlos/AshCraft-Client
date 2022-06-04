package top.lanscarlos.ashcraft.internet

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import top.lanscarlos.ashcraft.remote.RemoteOrder

interface OrderService {

    @FormUrlEncoded
    @POST("/order/query")
    fun query(@Field("userId") userId: Int): Call<List<RemoteOrder>>

    @FormUrlEncoded
    @POST("/order/purchase")
    fun purchase(@Field("userId") userId: Int): Call<RemoteOrder>

}