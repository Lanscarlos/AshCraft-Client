package top.lanscarlos.ashcraft

import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.util.mutableLiveOf

class AshCraftContext : ViewModel() {

    companion object {

        val theme by lazy { mutableLiveOf(R.style.pink) }

    }
}