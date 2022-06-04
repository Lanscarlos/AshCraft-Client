package top.lanscarlos.ashcraft.pojo

import top.lanscarlos.ashcraft.pojo.Commodity.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteCartItem

/**
 * 代表购物车子项
 * */
class CartItem(
    remote: RemoteCartItem
) {

    val id = remote.id
    var selected = remote.isSelected
    val commodity = remote.commodity.fixed()
    var amount = remote.amount
    var totalPrice = remote.totalPrice

    val name get() = commodity.name
    val lore get() = commodity.lore
    val price get() = commodity.price
    val image get() = commodity.image

    companion object {
        fun RemoteCartItem.fixed(): CartItem = CartItem(this)
    }
}