package top.lanscarlos.ashcraft

import androidx.annotation.StyleRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.Cart
import top.lanscarlos.ashcraft.pojo.HomePagerItem
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.util.mutableLiveOf

class AshCraftContext : ViewModel() {

    companion object {

        val theme by lazy { mutableLiveOf(R.style.pink) }

        private val _user = mutableLiveOf(User())
        val user get() = _user.value

        private val _cart = mutableLiveOf(Cart())
        val cart get() = _cart.value

        val homePagers = mutableListOf(
            HomePagerItem("TB1w.KnuaL7gK0jSZFBXXXZZpXa-970-763.png"),
            HomePagerItem("TB1FywYtUT1gK0jSZFrXXcNCXXa-970-763.png"),
            HomePagerItem("TB1wqZWtKT2gK0jSZFvXXXnFXXa-970-763.png"),
        )

    }
}