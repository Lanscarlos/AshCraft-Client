package top.lanscarlos.ashcraft.pojo

import top.lanscarlos.ashcraft.pojo.Commodity.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteOrderItem

class OrderItem(
    remote: RemoteOrderItem
) {

    val id = remote.id
    val amount = remote.amount
    val totalPrice = remote.totalPrice
    val commodity = remote.commodity.fixed()

    companion object {
        fun RemoteOrderItem.fixed(): OrderItem = OrderItem(this)
    }
}