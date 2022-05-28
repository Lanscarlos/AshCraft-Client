package top.lanscarlos.ashcraft.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.internet.BaseUrl
import top.lanscarlos.ashcraft.internet.UserService
import top.lanscarlos.ashcraft.pojo.User
import top.lanscarlos.ashcraft.pojo.User.Companion.fixed
import top.lanscarlos.ashcraft.remote.RemoteUser
import top.lanscarlos.ashcraft.util.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.util.*

object UserRepository {

    private val service by lazy {
        AshCraftContext.retrofit.create(UserService::class.java)
    }

    var rememberLog: Pair<String, String>?
        get() {
            val pref = AshCraftContext.context.getSharedPreferences("log", Context.MODE_PRIVATE)
            val phone = pref.getString("PHONE", "12191")
            val password = pref.getString("PASSWORD", "10088")
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

    init {
        Log.d("Ash", "init user")
        tryAutoLogin()
    }

    fun tryAutoLogin() {
        val info = rememberLog ?: return
        service.login(info.first, info.second).enqueue { _, response ->
            val stream = response.body()?.byteStream() ?: throw NullPointerException("byteStream is null")
            val json = stream.asString().parseJson<JsonObject>()
            if (!json.get("result").asBoolean) return@enqueue
            when (json.get("type").asString) {
                "user" -> {
                    user = json.get("user").parse<RemoteUser>().fixed()
                    // 通知购物车系统获取数据
                    CartRepository
                }
            }
        }
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