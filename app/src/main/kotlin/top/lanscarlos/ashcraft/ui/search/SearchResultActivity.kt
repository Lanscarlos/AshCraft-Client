package top.lanscarlos.ashcraft.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.databinding.ActivitySearchResultBinding
import top.lanscarlos.ashcraft.pojo.Nameable
import top.lanscarlos.ashcraft.repository.CommodityRepository
import top.lanscarlos.ashcraft.repository.ShopRepository

class SearchResultActivity : AppCompatActivity() {

    private var _binding: ActivitySearchResultBinding? = null
    private val binding get() = _binding!!

    private val tabsTitle = listOf("全部", "商品", "店铺")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivitySearchResultBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val data = mutableListOf<Nameable>().apply {
            addAll(CommodityRepository.commodities)
            addAll(ShopRepository.shops)
        }

        val search = intent?.getStringExtra("search")
        val result = data.filter {
            if (search!= null) it.name.startsWith(search) else true
        }

        val viewPager = binding.viewPager
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabsTitle.size
            override fun createFragment(position: Int): Fragment {
                return SearchResultFragment(position, result)
            }
        }

        val tabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsTitle[position]
        }
        // tabs & viewPager 联动
        mediator.attach()

        binding.back.setOnClickListener {
            finish()
        }

        binding.searchInput.setText(intent.getStringExtra("search") ?: "")
        binding.searchInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) return@setOnFocusChangeListener
            v.clearFocus()
            finish()
        }
    }
}