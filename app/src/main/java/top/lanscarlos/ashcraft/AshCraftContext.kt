package top.lanscarlos.ashcraft

import androidx.annotation.StyleRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.util.mutableLiveOf

class AshCraftContext : ViewModel() {

    companion object {

        val theme by lazy { mutableLiveOf(R.style.pink) }

    }
}