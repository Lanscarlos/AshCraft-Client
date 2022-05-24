package top.lanscarlos.ashcraft.pojo

import top.lanscarlos.ashcraft.pojo.CartItem.Companion.fixed
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteCart

class Cart(
    remote: RemoteCart
) {

    val user = remote.user.fixed()
    val items: List<CartItem> = remote.items.map { it.fixed() }.toList()
    var totalPrice = remote.totalPrice

    companion object {
        fun RemoteCart.fixed(): Cart = Cart(this)
    }
}