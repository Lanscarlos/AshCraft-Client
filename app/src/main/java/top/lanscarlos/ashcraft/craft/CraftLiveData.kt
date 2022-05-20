package top.lanscarlos.ashcraft.craft

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.lang.NullPointerException

class CraftLiveData<T> : MutableLiveData<T> {

    private var cache = false

    constructor() : super()
    constructor(value: T) : super(value)

    override fun getValue(): T {
        return super.getValue() ?: throw NullPointerException("CraftLiveData value must not null!")
    }

    override fun setValue(value: T) {
        if (this.value == value) return
        super.setValue(value)
    }

    fun observe(owner: LifecycleOwner, runFirst: Boolean = false, func: (T) -> Unit) {
        // 重置 cache 状态
        cache = false
        super.observe(owner) inner@{
            if (!cache && !runFirst) {
                cache = true
                return@inner
            }
            func(it)
        }
    }

}