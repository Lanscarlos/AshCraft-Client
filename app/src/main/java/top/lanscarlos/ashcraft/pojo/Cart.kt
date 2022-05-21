package top.lanscarlos.ashcraft.pojo

class Cart {

    private val _items: MutableList<CartItem> = mutableListOf()

    val items: List<CartItem> get() = _items

    // 已选商品数量
    var selected = 0
    var totalPrice = 0.0

    private val onItemsSizeChanged: MutableList<((size: Int, isAdd: Boolean) -> Unit)> = mutableListOf()

    fun isAllSelect(): Boolean = selected == items.size

    fun addItem(item: Treasure) {
        addItem(CartItem(item, 1, item.price))
    }

    fun addItem(item: CartItem) {
        _items += item
        onItemsSizeChanged.forEach { it(_items.size, true) }
    }

    fun removeAt(index: Int) {
        _items.removeAt(index)
        onItemsSizeChanged.forEach { it(_items.size, false) }
    }

    fun addOnItemsSizeChanged(func: ((size: Int, isAdd: Boolean) -> Unit)) {
        onItemsSizeChanged += func
    }

    fun updateTotalPrice(totalPrice: Double) {
        this.totalPrice = totalPrice
    }

    fun updateTotalPrice(selected: ((index: Int, item: Cart.CartItem) -> Boolean)): Double {
        var totalPrice = 0.0
        for ((i, item) in items.withIndex()) {
            if (selected(i, item)) totalPrice += item.totalPrice
        }
        this.totalPrice = totalPrice
        return totalPrice
    }

    /**
     * 代表购物车子项
     * */
    inner class CartItem(
        val treasure: Treasure,
        var amount: Int,
        var totalPrice: Double
    ) {
        val name get() = treasure.name
        val lore get() = treasure.lore
        val price get() = treasure.price
        val img get() = treasure.img
    }

}