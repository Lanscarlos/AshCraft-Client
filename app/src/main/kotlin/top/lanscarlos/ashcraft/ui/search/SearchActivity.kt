package top.lanscarlos.ashcraft.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    private val tabsTitle = listOf("全部", "商品", "店铺")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivitySearchBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabsTitle.size
            override fun createFragment(position: Int): Fragment {
                return SearchResultFragment(tabsTitle[position])
            }
        }

        val tabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsTitle[position]
        }
        // tabs & viewPager 联动
        mediator.attach()

        binding.searchInput.setText(intent.getStringExtra("search") ?: "")
    }
}