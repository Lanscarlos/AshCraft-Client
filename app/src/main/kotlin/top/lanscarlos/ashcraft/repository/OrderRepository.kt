package top.lanscarlos.ashcraft.repository

import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.OrderService
import top.lanscarlos.ashcraft.pojo.Order
import top.lanscarlos.ashcraft.pojo.Order.Companion.fixed
import top.lanscarlos.ashcraft.util.enqueue

object OrderRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(OrderService::class.java)
    }

    val orders = mutableListOf<Order>()

    fun tryAccessData(onResponse: (List<Order>?) -> Unit) {
        val userId = UserRepository.user?.id
        if (userId == null) {
            onResponse(null)
            return
        }
        service.query(userId).enqueue(onFailure = { _, _ ->
            onResponse(listOf())
        }) { _, response ->
            response.body()?.let {
                orders.clear()
                orders.addAll(it.map { item ->  item.fixed() })
                onResponse(orders)
            }
        }
    }

    fun tryPurchase(onResponse: (Order?) -> Unit) {
        val userId = UserRepository.user?.id
        if (userId == null) {
            onResponse(null)
            return
        }
        service.purchase(userId).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            response.body()?.fixed()?.let {
                orders.add(it)
                onResponse(it)
            }
            UserRepository.refresh()
        }
    }

}