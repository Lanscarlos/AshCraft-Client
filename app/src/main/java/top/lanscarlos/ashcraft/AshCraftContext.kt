package top.lanscarlos.ashcraft

import androidx.annotation.StyleRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AshCraftContext : ViewModel() {

    companion object {

        private val _theme by lazy { MutableLiveData(R.style.pink) }
        val theme: LiveData<Int> get() = _theme

        fun setTheme(@StyleRes style: Int) {
            _theme.value = style
        }
    }
}