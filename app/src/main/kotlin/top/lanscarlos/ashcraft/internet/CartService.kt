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

    @FormUrlEncoded
    @POST("/cart/select")
    fun select(
        @Field("cartId") cartId: Int,
        @Field("itemId") itemId: Int,
        @Field("selected") selected: Boolean,
    ): Call<RemoteCart>

    @FormUrlEncoded
    @POST("/cart/add")
    fun addItem(
        @Field("cartId") cartId: Int,
        @Field("commodityId") commodityId: Int,
    ): Call<RemoteCart>

    @FormUrlEncoded
    @POST("/cart/remove")
    fun removeItem(
        @Field("cartId") cartId: Int,
        @Field("itemId") itemId: Int,
    ): Call<RemoteCart>

    @FormUrlEncoded
    @POST("/cart/increase")
    fun increaseAmount(
        @Field("cartId") cartId: Int,
        @Field("itemId") itemId: Int,
    ): Call<RemoteCart>

    @FormUrlEncoded
    @POST("/cart/decrease")
    fun decreaseAmount(
        @Field("cartId") cartId: Int,
        @Field("itemId") itemId: Int,
    ): Call<RemoteCart>

}