package top.lanscarlos.ashcraft.pojo

import android.annotation.SuppressLint
import top.lanscarlos.ashcraft.pojo.OrderItem.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteOrder
import java.text.SimpleDateFormat

class Order(
    remote: RemoteOrder
) {

    val id = remote.id
    @SuppressLint("SimpleDateFormat")
    val purchaseTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(remote.purchaseTime)
    val totalPrice = remote.totalPrice
    val items = remote.items.map { it.fixed() }

    companion object {
        fun RemoteOrder.fixed(): Order = Order(this)
    }

}