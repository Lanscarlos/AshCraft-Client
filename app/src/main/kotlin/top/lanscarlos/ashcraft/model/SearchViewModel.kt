package top.lanscarlos.ashcraft.model

import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.pojo.Nameable
import top.lanscarlos.ashcraft.repository.CommodityRepository
import top.lanscarlos.ashcraft.repository.ShopRepository
import top.lanscarlos.ashcraft.util.mutableLiveOf

class SearchViewModel : ViewModel() {

    val commodities = mutableListOf<Nameable>().apply {
        addAll(CommodityRepository.commodities)
        addAll(ShopRepository.shops)
    }

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
        return commodities.mapNotNull {
            if (it.name.startsWith(input)) it.name else null
        }
    }

}