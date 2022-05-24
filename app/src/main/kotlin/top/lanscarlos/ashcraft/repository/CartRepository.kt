package top.lanscarlos.ashcraft.repository

import top.lanscarlos.ashcraft.pojo.Cart
import top.lanscarlos.ashcraft.pojo.CartItem
import top.lanscarlos.ashcraft.remote.RemoteCart
import top.lanscarlos.ashcraft.remote.RemoteCartItem
import top.lanscarlos.ashcraft.remote.RemoteCommodity
import top.lanscarlos.ashcraft.remote.RemoteUser
import java.util.*

object CartRepository {

    lateinit var cart: Cart
    val items get() = cart.items

    init {
        cart = Cart(
            RemoteCart(
                10086,
                RemoteUser(
                    10086,
                    2,
                    "头像",
                    "刘华强",
                    "你这瓜有问题啊！",
                    "123456",
                    96.0,
                    "新宝岛",
                    "114514",
                    Date()
                ),
                0.0,
                setOf(
                    RemoteCartItem(
                        10086,
                        RemoteCommodity(
                            10086,
                            "鸡汤",
                            "这喝汤多是一件美逝啊！",
                            19.9,
                            16,
                            "https://img.alicdn.com/bao/uploaded/i2/833272552/O1CN0134vEK11UirL5Rt6Ds_!!833272552.jpg_200x200q90.jpg_.webp",
                            "食品",
                            null
                        ),
                        1,
                        19.0
                    ),
                    RemoteCartItem(
                        10086,
                        RemoteCommodity(
                            10086,
                            "小板凳",
                            "这喝汤多是一件美逝啊！",
                            9.9,
                            16,
                            "https://img.alicdn.com/imgextra/i4/2221906611/O1CN01uxDtGx1yht8sO7KKm_!!2221906611-0-alimamacc.jpg_200x200q90.jpg_.webp",
                            "食品",
                            null
                        ),
                        1,
                        9.9
                    ),
                    RemoteCartItem(
                        10086,
                        RemoteCommodity(
                            10086,
                            "好吧",
                            "这喝汤多是一件美逝啊！",
                            29.9,
                            16,
                            "https://img.alicdn.com/imgextra/i1/3240744774/O1CN01PAZ0ZE1l8XOOfQpC1_!!3240744774-0-daren.jpg_180x180xzq90.jpg_.webp",
                            "食品",
                            null
                        ),
                        1,
                        29.9
                    ),
                )
            )
        )
    }

}