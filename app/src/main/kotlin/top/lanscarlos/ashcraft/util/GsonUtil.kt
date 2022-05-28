package top.lanscarlos.ashcraft.util

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

val gson by lazy { Gson() }

fun InputStream.asString(): String {
    val sb = StringBuffer()
    use {
        BufferedReader(InputStreamReader(it)).forEachLine { line -> sb.append(line) }
    }
    return sb.toString()
}

inline fun <reified T> String.parseJson(): T {
    return gson.fromJson(this, T::class.java)
}

inline fun <reified T> JsonElement.parse(): T {
    return gson.fromJson(this, T::class.java)
}