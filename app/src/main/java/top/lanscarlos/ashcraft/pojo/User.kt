package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import top.lanscarlos.ashcraft.pojo.remote.RemoteUser

class User(remote: RemoteUser? = null) {

    val avatar: Bitmap?
    val name = remote?.name ?: "请登录"
    val signature = remote?.signature ?: "登录即可解锁特权"
    val money = remote?.money ?: 0.0

    init {
        avatar = null
    }

}