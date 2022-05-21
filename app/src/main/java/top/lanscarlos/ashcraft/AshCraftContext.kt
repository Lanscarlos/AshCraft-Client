package top.lanscarlos.ashcraft

import androidx.annotation.StyleRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.util.mutableLiveOf

class AshCraftContext : ViewModel() {

    companion object {

        val theme by lazy { mutableLiveOf(R.style.pink) }

        private val _items: MutableList<Treasure> = mutableListOf()
        private val onItemsSizeChanged: MutableList<((size: Int, isAdd: Boolean) -> Unit)> = mutableListOf()

        val items: List<Treasure> = _items

        fun addItem(item: Treasure) {
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

    }
}