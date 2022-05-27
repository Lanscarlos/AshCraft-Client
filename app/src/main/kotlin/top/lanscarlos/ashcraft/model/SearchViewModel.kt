package top.lanscarlos.ashcraft.model

import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.util.mutableLiveOf

class SearchViewModel : ViewModel() {

    val result = listOf(
        "派蒙杯",
        "派蒙手办",
        "派蒙画像",
        "板栗粽子",
        "绿豆雪糕"
    )

    /*
    * 搜索提示
    * */
    val tips = mutableLiveOf(listOf<String>())

    val input = mutableLiveOf("")

    fun setInput(input: String) {
        this.input.value = input
        tips.setValue(getSearchTips(input), true)
    }

    /**
     * 获取搜索提示
     * */
    fun getSearchTips(input: String): List<String> {
        if (input.isEmpty()) return listOf()
        return result.mapNotNull {
            if (it.startsWith(input)) it else null
        }
    }

}