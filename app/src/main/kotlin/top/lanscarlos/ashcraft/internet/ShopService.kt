package top.lanscarlos.ashcraft.internet

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import top.lanscarlos.ashcraft.remote.RemoteShop

interface ShopService {

    @FormUrlEncoded
    @POST("/seller/query")
    fun query(@Field("keyword") keyword: String? = null): Call<List<RemoteShop>>

    @GET("/images/{image}.png")
    fun image(@Path("image") image: String): Call<ResponseBody>

}