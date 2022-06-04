package top.lanscarlos.ashcraft.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.JsonObject
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.UserService
import top.lanscarlos.ashcraft.pojo.Cart
import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteUser
import top.lanscarlos.ashcraft.util.*
import java.lang.NullPointerException
import java.util.*

object UserRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(UserService::class.java)
    }

    var rememberLog: Pair<String, String>?
        get() {
            val pref = AshCraftContext.context.getSharedPreferences("log", Context.MODE_PRIVATE)
            val phone = pref.getString("PHONE", null)
            val password = pref.getString("PASSWORD", null)
            return if (phone != null && password != null) {
                phone to password
            } else null
        }
        set(value) {
            AshCraftContext.context.getSharedPreferences("log", Context.MODE_PRIVATE).edit {
                putString("PHONE", value?.first)
                putString("PASSWORD", value?.second)
                apply()
            }
        }

    var user: User? = null
        private set

    var onRefreshListener: ((User?) -> Unit)? = null

    init {
        Log.d("Ash", "init user")
        tryAutoLogin()
    }

    fun tryAutoLogin() {
        val info = rememberLog ?: return
        tryLogin(info.first, info.second) {
            if (it == null) return@tryLogin
            // 通知购物车系统获取数据
            CartRepository
        }
    }

    fun tryLogin(phone: String, password: String, onResponse: (User?) -> Unit = {}) {
        service.login(phone, password).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            val stream = response.body()?.byteStream() ?: throw NullPointerException("byteStream is null")
            val json = stream.asString().parseJson<JsonObject>()
//            if (!json.get("result").asBoolean) return@enqueue
            when (json.get("type").asString) {
                "user" -> {
                    user = json.get("user").parse<RemoteUser>().fixed()
                    rememberLog = phone to password
                    CartRepository.refresh()
                    refresh()
                    onResponse(user)
                }
                else -> onResponse(null)
            }
        }
    }

    fun tryRegister(phone: String, name: String, password: String, onResponse: (User?) -> Unit = {}) {
        service.register(
            phone, name, password,
            signature = "这个人很懒什么也没写~",
            address = "日内瓦",
            money = (0..10000).random().toDouble()
        ).enqueue(onFailure = { _, _ ->
            onResponse(null)
        }) { _, response ->
            rememberLog = phone to password
            onResponse(response.body()?.fixed())
        }
    }

    fun logout() {
        user = null
        refresh()
        CartRepository.refresh()
    }

    fun refresh() {
        onRefreshListener?.let { it(user) }
    }

    fun setOnRefresh(onChanged: ((User?) -> Unit)?) {
        onRefreshListener = onChanged
    }

    private fun analogInit() {
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
                Date().time
            )
        )
    }
}