package top.lanscarlos.ashcraft.repository

import android.graphics.BitmapFactory
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.CommodityService
import top.lanscarlos.ashcraft.pojo.Commodity
import top.lanscarlos.ashcraft.pojo.Commodity.Companion.fixed
import top.lanscarlos.ashcraft.util.enqueue

object CommodityRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(CommodityService::class.java)
    }

    var commodities: List<Commodity> = listOf()

    init {
        tryAccessData()
    }

    fun tryAccessData() {
        service.query().enqueue { _, response ->
            commodities = response.body()?.map {
                val commodity = it.fixed()
                service.image(it.image).enqueue inner@{ _, response ->
                    val stream = response.body()?.byteStream() ?: return@inner
                    val bitmap = BitmapFactory.decodeStream(stream)
                    commodity.image = bitmap
                }
                commodity
            } ?: listOf()
        }
    }

}