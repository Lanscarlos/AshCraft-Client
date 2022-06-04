package top.lanscarlos.ashcraft.repository

import android.util.Log
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.CartService
import top.lanscarlos.ashcraft.pojo.Cart
import top.lanscarlos.ashcraft.pojo.Cart.Companion.fixed
import top.lanscarlos.ashcraft.pojo.CartItem
import top.lanscarlos.ashcraft.pojo.Commodity
import top.lanscarlos.ashcraft.remote.RemoteCart
import top.lanscarlos.ashcraft.remote.RemoteCartItem
import top.lanscarlos.ashcraft.remote.RemoteCommodity
import top.lanscarlos.ashcraft.util.enqueue

object CartRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(CartService::class.java)
    }

    var cart: Cart? = null
        private set

    val items get() = cart?.items

    var onRefreshListener: ((Cart?) -> Unit)? = null

    init {
        Log.d("Ash", "init cart")
        tryAccessData()
    }

    fun tryAccessData(callChange: Boolean = true, onResponse: (Cart?) -> Unit = {}) {
        val userId = (if (UserRepository.user == null) {
            onResponse(null)
            null
        } else UserRepository.user?.id) ?: return
        service.getCart(userId).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            cart = response.body()?.fixed()
            onResponse(cart)
            if (callChange) {
                onRefreshListener?.let { it(cart) }
            }
        }
    }

    fun selectItem(itemId: Int, selected: Boolean, onResponse: (Cart?) -> Unit = {}) {
        val cart = cart ?: return
        service.select(cart.id, itemId, selected).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            this.cart = response.body()?.fixed()
            onResponse(this.cart)
//            onChangedListener?.let { it(this.cart) }
        }
    }

    fun addItem(commodity: Commodity, onResponse: (Cart?) -> Unit = {}) {
        val cart = cart ?: return
        service.addItem(cart.id, commodity.id).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            this.cart = response.body()?.fixed()
            onResponse(this.cart)
            onRefreshListener?.let { it(this.cart) }
        }
    }

    fun removeItem(item: CartItem, onResponse: (Cart?) -> Unit = {}) {
        val cart = cart ?: return
        service.removeItem(cart.id, item.id).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            this.cart = response.body()?.fixed()
//            onChangedListener?.let { it(this.cart) }
        }
    }

    fun increaseAmount(item: CartItem, onResponse: (Cart?) -> Unit) {
        val cart = cart ?: return
        service.increaseAmount(cart.id, item.id).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            this.cart = response.body()?.fixed()
            onResponse(this.cart)
//            onChangedListener?.let { it(this.cart) }
        }
    }

    fun decreaseAmount(item: CartItem, onResponse: (Cart?) -> Unit) {
        val cart = cart ?: return
        service.decreaseAmount(cart.id, item.id).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            this.cart = response.body()?.fixed()
            onResponse(this.cart)
//            onChangedListener?.let { it(this.cart) }
        }
    }

    fun refresh() {
        val userId = UserRepository.user?.id
        if (userId == null) {
            onRefreshListener?.let { it(cart) }
            return
        }
        service.getCart(userId).enqueue { _, response ->
            cart = response.body()?.fixed()
            onRefreshListener?.let { it(cart) }
        }
    }

    fun setOnRefresh(onChanged: ((Cart?) -> Unit)?) {
        onRefreshListener = onChanged
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