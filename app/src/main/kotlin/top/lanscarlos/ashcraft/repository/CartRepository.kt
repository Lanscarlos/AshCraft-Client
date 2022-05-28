package top.lanscarlos.ashcraft.repository

import android.util.Log
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.CartService
import top.lanscarlos.ashcraft.internet.UserService
import top.lanscarlos.ashcraft.pojo.Cart
import top.lanscarlos.ashcraft.pojo.Cart.Companion.fixed
import top.lanscarlos.ashcraft.pojo.CartItem
import top.lanscarlos.ashcraft.remote.RemoteCart
import top.lanscarlos.ashcraft.remote.RemoteCartItem
import top.lanscarlos.ashcraft.remote.RemoteCommodity
import top.lanscarlos.ashcraft.remote.RemoteUser
import top.lanscarlos.ashcraft.util.enqueue
import java.lang.NullPointerException
import java.util.*

object CartRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(CartService::class.java)
    }

    var cart: Cart? = null
        private set

    val items get() = cart?.items

    init {
        Log.d("Ash", "init cart")
        tryAccessData()
    }

    fun tryAccessData(onResponse: (Cart?) -> Unit = {}) {
        val userId = UserRepository.user?.id ?: return
        service.getCart(userId).enqueue { _, response ->
            cart = response.body()?.also { Log.d("Ash", "cart -> $it") }?.fixed()
            onResponse(cart)
        }
    }

    private fun analogInit() {
        cart = Cart(
            RemoteCart(
                10086,
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