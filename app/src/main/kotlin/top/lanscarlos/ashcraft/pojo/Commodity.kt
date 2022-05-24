package top.lanscarlos.ashcraft.pojo

import top.lanscarlos.ashcraft.remote.RemoteCommodity

class Commodity(
    remote: RemoteCommodity
) {

    val name = remote.name
    val lore = remote.introduce
    val price = remote.price
    val image = remote.image
    val storage = remote.storage
    val category = remote.category
    val shop = remote.seller

    companion object {
        fun RemoteCommodity.fixed(): Commodity = Commodity(this)
    }
}