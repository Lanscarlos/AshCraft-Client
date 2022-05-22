package top.lanscarlos.ashcraft

import android.net.Uri
import androidx.annotation.StyleRes
import androidx.core.net.toUri
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
            HomePagerItem("https://imgcps.jd.com/ling4/100026667910/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6e2/a0b459cd/cr/s/q.jpg".toUri()),
            HomePagerItem("https://imgcps.jd.com/ling4/10032300178906/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6f6/fcacabb1/cr/s/q.jpg".toUri()),
            HomePagerItem("https://imgcps.jd.com/ling4/10047704922891/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6f5/5b8bd51b/cr/s/q.jpg".toUri()),
            HomePagerItem("https://imgcps.jd.com/ling4/100008347222/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6ee/c9c2aa56/cr/s/q.jpg".toUri()),
        )

    }
}