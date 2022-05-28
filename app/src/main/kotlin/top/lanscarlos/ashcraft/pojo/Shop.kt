package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import android.util.Log
import androidx.core.util.toRange
import top.lanscarlos.ashcraft.remote.RemoteShop
import java.text.SimpleDateFormat
import java.util.*

class Shop(
    remote: RemoteShop
) : Nameable {

    val id = remote.id
    override val name = remote.name
    var avatar: Bitmap? = null
    val description = remote.description
    val contact = remote.contact
    val regTime = Date(remote.regTime)
    val rating = ((20..50).random() / 10.0).toFloat()
    val age: Int

    init {
        val old = Calendar.getInstance().apply { time = regTime }
        val now = Calendar.getInstance()
        age = now.get(Calendar.YEAR) - old.get(Calendar.YEAR)
    }

    companion object {
        fun RemoteShop.fixed(): Shop = Shop(this)
    }
}