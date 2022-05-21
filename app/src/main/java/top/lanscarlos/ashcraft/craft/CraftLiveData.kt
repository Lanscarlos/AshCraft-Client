package top.lanscarlos.ashcraft.craft

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.lang.NullPointerException

class CraftLiveData<T> : MutableLiveData<T> {

    private var cache = false
    private var silent = false

    val valueOrNull: T? = super.getValue()

    constructor() : super()
    constructor(value: T) : super(value)

    override fun getValue(): T {
        return super.getValue() ?: throw NullPointerException("CraftLiveData value must not null!")
    }

    fun getValue(def: T): T {
        return super.getValue() ?: def
    }

    override fun setValue(value: T) {
        if (this.value == value) return
        super.setValue(value)
    }

    /**
     * 静默设置值，即不触发改变事件
     * */
    fun setValueSilent(value: T) {
        this.silent = true
        this.value = value
    }

    fun observe(owner: LifecycleOwner, runFirst: Boolean = false, func: (T) -> Unit) {
        // 重置 cache 状态
        cache = false
        super.observe(owner) inner@{
            if (silent) {
                silent = false
                return@inner
            }else if (!cache && !runFirst) {
                cache = true
                return@inner
            }
            func(it)
        }
    }

}