package top.lanscarlos.ashcraft.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteUser
import top.lanscarlos.ashcraft.util.mutableLiveOf
import java.util.*

class ProfileViewModel : ViewModel() {

    val user = mutableLiveOf(
        RemoteUser(
            10086,
            2,
            "头像",
            "刘华强",
            "你这瓜有问题啊！",
            "123456",
            96.0,
            "新宝岛",
            "114514",
            Date()
        ).fixed())
    val collapsed = mutableLiveOf(false)

}