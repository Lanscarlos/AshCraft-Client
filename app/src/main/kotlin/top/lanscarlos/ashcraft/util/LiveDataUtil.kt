package top.lanscarlos.ashcraft.util

import top.lanscarlos.ashcraft.craft.CraftLiveData

/**
 * 获取增强型 LiveData
 * */
fun <T> mutableLiveOf(): CraftLiveData<T> {
    return CraftLiveData()
}

/**
 * 获取增强型 LiveData
 * */
fun <T> mutableLiveOf(
    value: T,
    preObserve: ((value: T) -> Unit)? = null,
    postObserve: ((value: T) -> Unit)? = null,
): CraftLiveData<T> {
    return CraftLiveData(value, preObserve, postObserve)
}