package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import top.lanscarlos.ashcraft.pojo.Shop.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteCommodity

class Commodity(
    remote: RemoteCommodity
) : Nameable {

    val id = remote.id
    override val name = remote.name
    val lore = remote.description
    val price = remote.price
    var image: Bitmap? = null
    val storage = remote.storage
    val category = remote.category
    val shop = remote.seller.fixed()

    companion object {
        fun RemoteCommodity.fixed(): Commodity = Commodity(this)
    }
}