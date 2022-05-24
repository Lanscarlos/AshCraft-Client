package top.lanscarlos.ashcraft.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.model.HomeViewModel

class HomePagerAdapter(
    home: HomeFragment,
    val viewModel: HomeViewModel
) : FragmentStateAdapter(home) {

    val pagers = viewModel.homePagers

    override fun getItemCount(): Int {
       return if (pagers.isNotEmpty()) pagers.size + 2 else 0
    }

    fun getActualPosition(position: Int): Int {
        return when(position) {
            0 -> pagers.size - 1
            itemCount - 1 -> 0
            else -> position - 1
        }
    }

    override fun createFragment(position: Int): Fragment {
        return HomePagerFragment(pagers[getActualPosition(position)])
    }

}