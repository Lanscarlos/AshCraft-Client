package top.lanscarlos.ashcraft.repository

import android.graphics.BitmapFactory
import android.util.Log
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.ShopService
import top.lanscarlos.ashcraft.pojo.Shop
import top.lanscarlos.ashcraft.pojo.Shop.Companion.fixed
import top.lanscarlos.ashcraft.util.enqueue

object ShopRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(ShopService::class.java)
    }

    var shops: List<Shop> = listOf()

    init {
        tryAccessData()
    }

    fun tryAccessData() {
        service.query().enqueue { _, response ->
            shops = response.body()?.map {
                val shop = it.fixed()
                it.avatar?.let { avatar ->
                    service.image(avatar).enqueue inner@{ _, response ->
                        val stream = response.body()?.byteStream() ?: return@inner
                        val bitmap = BitmapFactory.decodeStream(stream)
                        shop.avatar = bitmap
                    }
                }
                shop
            } ?: listOf()
        }
    }

}