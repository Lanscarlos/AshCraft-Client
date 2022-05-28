package top.lanscarlos.ashcraft.model

import android.util.Log
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.CartItem
import top.lanscarlos.ashcraft.repository.CartRepository
import top.lanscarlos.ashcraft.util.mutableLiveOf

class CartViewModel : ViewModel() {

    private val silentSelecting = mutableLiveOf(false)
    private val selectedCount = mutableLiveOf(0)
    val itemSelected = mutableLiveOf(
        false,
        preObserve = { silentSelecting.value = true },
        postObserve = { silentSelecting.value = false },
    )
    val isAllSelected = mutableLiveOf(false)

    private val repository = CartRepository
    private var cart = repository.cart
    private val _items = cart?.items?.toMutableList()

    val items: List<CartItem> get() = _items!!
    val itemCount = mutableLiveOf(items.size)
    val totalPrice = mutableLiveOf(cart?.totalPrice ?: 0.0)

    fun refresh(onResponse: () -> Unit) {
        repository.tryAccessData {
            cart = it
            onResponse()
        }
    }

    fun selectItem(index: Int, selected: Boolean) {
        val cart = this.cart ?: return
        if (silentSelecting.value || index !in items.indices) return
        val item = items[index]
        if (selected) {
            selectedCount.value += 1
            cart.totalPrice += item.totalPrice

            // 预设全选状态
            if (selectedCount.value == items.size) {
                silentSelecting.value = true
                isAllSelected.value = true
            }
        } else {
            selectedCount.value -= 1
            cart.totalPrice -= item.totalPrice

            // 取消全选状态
            if (selectedCount.value == items.size - 1) {
                silentSelecting.value = true
                isAllSelected.value = false
            }
        }
        item.selected = selected
        totalPrice.value = cart.totalPrice
    }

    fun selectAll(selected: Boolean) {
        val cart = this.cart ?: return

        /*
        * 阻断事件重复回传
        * */
        if (silentSelecting.value) {
            silentSelecting.value = false
            return
        }

        /*
        * 静默修改全选数值
        * */
        isAllSelected.setValueSilent(selected)

        /*
        * 改变所有子选项的值
        * */
        itemSelected.value = selected

        cart.totalPrice = 0.0
        items.forEach {
            it.selected = selected
            if (selected) {
                cart.totalPrice += it.totalPrice
            }
        }
        selectedCount.value = if (selected) items.size else 0
        totalPrice.value = cart.totalPrice
    }

    fun removeItem(index: Int) {
        val cart = this.cart ?: return
        if (index !in items.indices) return
        val item = items[index]
        if (item.selected) {
            cart.totalPrice -= item.totalPrice
            totalPrice.value = cart.totalPrice
            selectedCount.value -= 1
        }
        itemCount.value -= 1

        _items!!.removeAt(index)

        /*
        *
        * 更新数据库内容
        * 待编辑...
        *
        * */
    }

    fun increaseItemAmount(index: Int): Int {
        val cart = this.cart ?: return -1
        if (index !in items.indices) return -1
        val item = items[index]

        /*
        * 添加数量
        * */
        item.amount += 1
        item.totalPrice += item.price

        /*
        * 检查是否需要修改总价
        * */
        if (item.selected) {
            cart.totalPrice += item.price
            totalPrice.value = cart.totalPrice
        }

        /*
        * 返回数值改变子项内容
        * */
        return item.amount
    }

    fun decreaseItemAmount(index: Int): Int {
        val cart = this.cart ?: return -1
        if (index !in items.indices) return -1
        val item = items[index]

        /*
        * 数量不能小于 1
        * */
        if (item.amount <= 1) return item.amount

        /*
        * 添加数量
        * */
        item.amount -= 1
        item.totalPrice -= item.price

        /*
        * 检查是否需要修改总价
        * */
        if (item.selected) {
            cart.totalPrice -= item.price
            totalPrice.value = cart.totalPrice
        }

        /*
        * 返回数值改变子项内容
        * */
        return item.amount
    }

}