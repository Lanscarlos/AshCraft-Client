package top.lanscarlos.ashcraft.internet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import top.lanscarlos.ashcraft.util.enqueue
import java.lang.NullPointerException

interface GenericService {

    /**
     * 获取泛用二进制流内容
     * 通常用于获取图片
     * */
    @GET("{path}")
    fun getResponse(@Path("path") path: String): Call<ResponseBody>

    companion object {

        private val services by lazy {
            BaseUrl.values.associate {
                it to Retrofit.Builder().apply {
                    baseUrl(it)
                    addConverterFactory(GsonConverterFactory.create())
                }.build().create(GenericService::class.java)
            }.toMutableMap()
        }

        private fun Uri.getService(baseUrl: String): GenericService {
            val url = if (scheme != null && host != null) {
                "$scheme://$host" + if (port > -1) ":$port/" else "/"
            } else baseUrl

            if (url !in services) {
                services[url] = Retrofit.Builder().apply {
                    baseUrl(url)
                    addConverterFactory(GsonConverterFactory.create())
                }.build().create(GenericService::class.java)
            }

            return services[url]!!
        }

        fun Uri.accessBitmap(
            url: String = "$scheme://$host" + if (port > -1) ":$port/" else "/",
            func: ((Bitmap) -> Unit)
        ) {
            val call = path?.let { getService(url).getResponse(it) } ?: throw NullPointerException("Base url must ")
            call.enqueue { _, response ->
                val stream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(stream)
                func(bitmap)
            }

        }
    }

}