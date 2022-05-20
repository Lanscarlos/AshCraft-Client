package top.lanscarlos.ashcraft.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _collapse = MutableLiveData(false)
    val collapse: LiveData<Boolean> = _collapse

    fun setCollapse(collapsed: Boolean) {
        if (collapse.value == collapsed) return
        _collapse.value = collapsed
    }
}