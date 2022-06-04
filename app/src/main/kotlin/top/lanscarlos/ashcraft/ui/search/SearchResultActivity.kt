package top.lanscarlos.ashcraft.ui.search

import android.content.Intent
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

        val keyword = intent?.getStringExtra("keyword")
        val result = if (keyword.equals("ALL", true) || keyword in listOf("全部", "所有")) {
            mutableListOf<Nameable>().apply {
                addAll(CommodityRepository.commodities)
                addAll(ShopRepository.shops)
            }
        } else if (keyword in listOf("全部商品", "所有商品")) {
            CommodityRepository.commodities
        } else if (keyword in listOf("全部店铺", "所有店铺")) {
            ShopRepository.shops
        } else {
            mutableListOf<Nameable>().apply {
                addAll(CommodityRepository.commodities)
                addAll(ShopRepository.shops)
            }.filter {
                if (keyword!= null) it.name.startsWith(keyword) else true
            }
        }

        binding.searchInput.setText(keyword ?: "")
        binding.searchInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) return@setOnFocusChangeListener
            v.clearFocus()
            startActivity(Intent(this, SearchPreviewActivity::class.java).apply {
                putExtra("keyword", keyword)
            })
            finish()
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
    }
}