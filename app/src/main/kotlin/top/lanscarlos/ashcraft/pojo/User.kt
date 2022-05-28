package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import top.lanscarlos.ashcraft.remote.RemoteUser

class User(
    remote: RemoteUser
) : Nameable {

    val id = remote.id
    val avatar: Bitmap? = null
    override val name = remote.name
    val signature = remote.signature
    val gender = remote.gender
    val money = remote.money

    companion object {
        fun RemoteUser.fixed(): User = User(this)
    }
}