package top.lanscarlos.ashcraft.repository

import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.remote.RemoteUser
import java.util.*

object UserRepository {

    lateinit var user: User

    init {
        user = User(
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
            )
        )
    }
}