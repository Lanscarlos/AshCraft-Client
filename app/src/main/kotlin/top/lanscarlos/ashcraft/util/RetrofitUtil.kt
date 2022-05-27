package top.lanscarlos.ashcraft.util

import android.net.Uri
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.enqueue(onFailure: ((call: Call<T>, t: Throwable) -> Unit) = { _, _ -> }, onResponse: ((call: Call<T>, response: Response<T>) -> Unit)) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse(call, response)
        }
        override fun onFailure(call: Call<T>, t: Throwable) {
            t.printStackTrace()
            onFailure(call, t)
        }
    })
}