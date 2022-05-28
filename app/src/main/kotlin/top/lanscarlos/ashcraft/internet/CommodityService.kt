package top.lanscarlos.ashcraft.internet

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import top.lanscarlos.ashcraft.remote.RemoteCommodity

interface CommodityService {

    @FormUrlEncoded
    @POST("/commodity/query")
    fun query(@Field("keyword") keyword: String? = null): Call<List<RemoteCommodity>>

    @GET("/images/{image}.png")
    fun image(@Path("image") image: String): Call<ResponseBody>

}