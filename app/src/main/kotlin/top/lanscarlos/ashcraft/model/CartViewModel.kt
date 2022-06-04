package top.lanscarlos.ashcraft.model

import android.util.Log
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.CartItem
import top.lanscarlos.ashcraft.repository.CartRepository
import top.lanscarlos.ashcraft.util.mutableLiveOf

class CartViewModel : ViewModel() {

    private val repository = CartRepository
    private val cart get() =  repository.cart
    val items get() =  cart?.items

    private val silentSelecting = mutableLiveOf(false)
    private val selectedCount = mutableLiveOf(0)
    val itemSelected = mutableLiveOf(
        cart?.isAllSelected ?: false,
        preObserve = { silentSelecting.value = true },
        postObserve = { silentSelecting.value = false },
    )
    val isAllSelected = mutableLiveOf(cart?.isAllSelected ?: false)
//    val items: List<CartItem> get() = _items!!
    val itemCount = mutableLiveOf(items?.size ?: 0)
    val totalPrice = mutableLiveOf(cart?.totalPrice ?: 0.0)

    fun refresh(onResponse: () -> Unit) {
        repository.tryAccessData(false) {
            silentSelecting.value = true
            selectedCount.setValueSilent(it?.selectCount ?: 0)
            itemSelected.setValueSilent(it?.isAllSelected ?: false)
            itemCount.value = items?.size ?: 0
            totalPrice.value = it?.totalPrice ?: 0.0
            isAllSelected.value = it?.isAllSelected ?: false
            silentSelecting.value = false
            onResponse()
        }
    }

    fun selectItem(index: Int, selected: Boolean) {
        val cart = this.cart ?: return
        val items = cart.items
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

        CartRepository.selectItem(item.id, selected) {
            totalPrice.value = it?.totalPrice ?: 0.0
        }
    }

    fun selectAll(selected: Boolean) {
        val cart = this.cart ?: return
        val items = cart.items

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

        CartRepository.selectItem(-1, selected) {
            selectedCount.value = if (selected) items.size else 0
            totalPrice.value = it?.totalPrice ?: 0.0
        }
    }

    fun removeItem(index: Int) {
        val cart = this.cart ?: return
        val items = cart.items
        if (index !in items.indices) return
        val item = items[index]
        if (item.selected) {
            cart.totalPrice -= item.totalPrice
            totalPrice.value = cart.totalPrice
            selectedCount.value -= 1
        }
        itemCount.value -= 1

        items.removeAt(index)
        CartRepository.removeItem(item)
    }

    fun increaseItemAmount(index: Int, onResponse: (Int) -> Unit) {
        val items = this.cart?.items ?: return
        if (index !in items.indices) return

        val id = items[index].id
        // 更新数据库数据
        CartRepository.increaseAmount(items[index]) { cart ->
            val item = cart?.items?.firstOrNull { id == it.id }
            onResponse(item?.amount ?: -1)
            totalPrice.value = cart?.totalPrice ?: 0.0
        }
    }

    fun decreaseItemAmount(index: Int, onResponse: (Int) -> Unit) {
        val items = this.cart?.items ?: return
        if (index !in items.indices) return

        val id = items[index].id
        // 更新数据库数据
        CartRepository.decreaseAmount(items[index]) { cart ->
            val item = cart?.items?.firstOrNull { id == it.id }
            onResponse(item?.amount ?: -1)
            totalPrice.value = cart?.totalPrice ?: 0.0
        }
    }

}