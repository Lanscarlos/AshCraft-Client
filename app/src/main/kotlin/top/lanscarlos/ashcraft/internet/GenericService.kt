package top.lanscarlos.ashcraft.internet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import top.lanscarlos.ashcraft.util.enqueue
import java.io.File
import java.lang.NullPointerException

interface GenericService {

    /**
     * 获取泛用二进制流内容
     * 通常用于获取图片
     * */
    @GET("{path}")
    fun getResponse(@Path("path") path: String): Call<ResponseBody>

    @Multipart
    @POST("Ashcraft_war_exploded/upload")
    fun uploadFile(
        @PartMap params: Map<String,String>,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    companion object {

        private val services by lazy {
            BaseUrl.values.associate {
                it to Retrofit.Builder().apply {
                    baseUrl(it)
                    addConverterFactory(GsonConverterFactory.create())
                }.build().create(GenericService::class.java)
            }.toMutableMap()
        }

        private fun Uri.getService(
            baseUrl: String? = null
        ): GenericService {
            val url = baseUrl ?: "$scheme://$host" + if (port > -1) ":$port/" else "/"
            if (url !in services) {
                services[url] = Retrofit.Builder().apply {
                    baseUrl(url)
                    addConverterFactory(GsonConverterFactory.create())
                }.build().create(GenericService::class.java)
            }

            return services[url]!!
        }

        fun Uri.accessBitmap(
            baseUrl: String? = null,
            func: ((Bitmap) -> Unit)
        ) {
            val call = path?.let { getService(baseUrl).getResponse(it) } ?: throw NullPointerException("Base url must not be null")
            call.enqueue { _, response ->
                val body = response.body() ?: throw NullPointerException("body must not be null")
                val stream = response.body()?.byteStream() ?: throw NullPointerException("stream must not be null")
                val bitmap = BitmapFactory.decodeStream(stream)
                func(bitmap)
            }

        }

//        fun Uri.uploadFile(
//            baseUrl: String? = null,
//            file: File
//        ) {
//            val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
//            val body = MultipartBody.Part.createFormData("exampleUp", file.name, requestFile)
//            val params = mutableMapOf("exam" to "ahhh")
//            val call = path?.let { getService(baseUrl).uploadFile(it, params, body) } ?: throw NullPointerException("Base url must not be null")
//            call.enqueue(onFailure = { _, t ->
//                Log.d("Ash", "Error!!!!")
//                t.printStackTrace()
//            }) { _, response ->
//                Log.d("Ash", "body -> ${response.body()}")
//                Log.d("Ash", "byteStream -> ${response.body()?.byteStream()}")
//                Log.d("Ash", "byteStream -> ${response.body()?.byteStream()?.readBytes() ?.let { String(it) }}")
//            }
//        }

//        fun testUpload() {
//            Log.d("Ash", "upload...")
//            val path = "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"
//            val dir = File(requireActivity().filesDir, "/images/")
//            val file = File(dir, path)
//
////            "http://172.17.32.120:8080/Ashcraft_war_exploded/upload".toUri().uploadFile(null, File(dir, path))
//
//            val retrofit = Retrofit.Builder().apply {
//                baseUrl("http://172.17.32.120:8080/")
//                addConverterFactory(GsonConverterFactory.create())
//            }.build().create(GenericService::class.java)
//
//            val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
//            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//            val params = mutableMapOf("exam" to "ahhh")
//
//            retrofit.uploadFile(params, body).enqueue(onFailure = { _, t ->
//                Log.d("Ash", "Error!!!!")
//                t.printStackTrace()
//            }) { _, response ->
//                Log.d("Ash", "response -> $response")
//                Log.d("Ash", "body -> ${response.body()}")
//                Log.d("Ash", "byteStream -> ${response.body()?.byteStream()}")
//                Log.d("Ash", "byteStream -> ${response.body()?.byteStream()?.readBytes() ?.let { String(it) }}")
//            }
//        }
    }

}