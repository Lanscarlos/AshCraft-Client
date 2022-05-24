package top.lanscarlos.ashcraft.craft

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.NullPointerException

class CraftLiveData<T> : MutableLiveData<T> {

    private var cache = false
    private var silent = false
    private var preObserve: ((value: T) -> Unit)? = null
    private var postObserve: ((value: T) -> Unit)? = null

//    val valueOrNull: T? = super.getValue()

    constructor() : super()
    constructor(
        value: T,
        preObserve: ((value: T) -> Unit)? = null,
        postObserve: ((value: T) -> Unit)? = null
    ) : super(value) {
        this.preObserve = preObserve
        this.postObserve = postObserve
    }

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
            preObserve?.let { pre -> pre(it) }
            func(it)
            postObserve?.let { post -> post(it) }
        }
    }
}