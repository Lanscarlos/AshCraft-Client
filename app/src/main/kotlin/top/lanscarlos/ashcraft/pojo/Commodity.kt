package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import top.lanscarlos.ashcraft.remote.RemoteCommodity

class Commodity(
    remote: RemoteCommodity
) : Nameable {

    override val name = remote.name
    val lore = remote.description
    val price = remote.price
    var image: Bitmap? = null
    val storage = remote.storage
    val category = remote.category
    val shop = remote.seller

    companion object {
        fun RemoteCommodity.fixed(): Commodity = Commodity(this)
    }
}