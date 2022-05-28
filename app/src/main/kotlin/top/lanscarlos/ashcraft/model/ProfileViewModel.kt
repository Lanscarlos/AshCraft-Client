package top.lanscarlos.ashcraft.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteUser
import top.lanscarlos.ashcraft.repository.UserRepository
import top.lanscarlos.ashcraft.util.mutableLiveOf
import java.util.*

class ProfileViewModel : ViewModel() {

    val user get() = UserRepository.user
    val money = mutableLiveOf(user?.money ?: 0.0)
    val collapsed = mutableLiveOf(false)

}