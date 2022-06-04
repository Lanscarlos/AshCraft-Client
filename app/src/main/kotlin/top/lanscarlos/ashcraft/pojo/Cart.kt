package top.lanscarlos.ashcraft.pojo

import top.lanscarlos.ashcraft.pojo.CartItem.Companion.fixed
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteCart

class Cart(
    remote: RemoteCart
) {

    val id = remote.id
    val items: MutableList<CartItem> = remote.items.map { it.fixed() }.toMutableList()
    var totalPrice = remote.totalPrice
    var isAllSelected = items.isNotEmpty()
    var selectCount = 0

    init {
        for (item in items) {
            if (item.selected) {
                selectCount += 1
            } else if (isAllSelected) {
                isAllSelected = false
            }
        }
    }



    companion object {
        fun RemoteCart.fixed(): Cart = Cart(this)
    }

    override fun toString(): String {
        return "Cart(id=$id, items=$items, totalPrice=$totalPrice, isAllSelected=$isAllSelected, selectCount=$selectCount)"
    }
}