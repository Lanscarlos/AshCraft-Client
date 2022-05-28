package top.lanscarlos.ashcraft

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.lanscarlos.ashcraft.internet.BaseUrl
import top.lanscarlos.ashcraft.repository.CommodityRepository
import top.lanscarlos.ashcraft.repository.ShopRepository
import top.lanscarlos.ashcraft.repository.UserRepository
import top.lanscarlos.ashcraft.util.mutableLiveOf
import java.lang.reflect.Type
import java.util.*

class AshCraftContext : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set

        val theme by lazy { mutableLiveOf(R.style.blue) }

        val baseUrl = BaseUrl.LANSCARLOS

        val retrofit by lazy {
            Retrofit.Builder().apply {
                baseUrl(baseUrl)
                addConverterFactory(CustomFactory())
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }

    }

    class CustomFactory() : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            Log.d("Ash", "responseBodyConverter...")
            return super.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

//        repeat(2) {
//            val uuid = UUID.randomUUID().toString().replace("-", "")
//            Log.d("Ash", "UUID -> $uuid")
//        }

//        Log.d("Ash", "2019 -> ${Date(2019 - 1900, 6, 15).time}")
//        Log.d("Ash", "2014 -> ${Date(2014 - 1900, 6, 15).time}")

        // 初始化 Commodity 数据
        CommodityRepository

        // 初始化 User 数据
        UserRepository

        // 初始化 Shop 数据
        ShopRepository
    }
}