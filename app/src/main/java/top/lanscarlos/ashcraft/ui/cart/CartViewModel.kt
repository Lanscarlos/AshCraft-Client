package top.lanscarlos.ashcraft.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.util.mutableLiveOf

class CartViewModel : ViewModel() {

    val isSelecting = mutableLiveOf(false)
    val isSelectAll = mutableLiveOf(true)

}