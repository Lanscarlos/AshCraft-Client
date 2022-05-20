package top.lanscarlos.ashcraft.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.util.mutableLiveOf

class ProfileViewModel : ViewModel() {

    val collapsed = mutableLiveOf(false)

}