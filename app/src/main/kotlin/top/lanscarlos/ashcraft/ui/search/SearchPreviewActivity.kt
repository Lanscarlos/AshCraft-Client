package top.lanscarlos.ashcraft.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivitySearchPreviewBinding
import top.lanscarlos.ashcraft.model.SearchViewModel

class SearchPreviewActivity : AppCompatActivity() {

    private var _binding: ActivitySearchPreviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivitySearchPreviewBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        /*
        * 初始化列表视图组件
        * */
        val adapter = SearchPreviewAdapter(this, viewModel)
        binding.items.adapter = adapter
        // 添加自定义分隔线
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_search)!!)
        binding.items.addItemDecoration(divider)

        // 搜索框事件处理
        val searchInput = binding.searchInput
        searchInput.hint = viewModel.commodities.randomOrNull()?.name ?: ""
        searchInput.addTextChangedListener {
            viewModel.setInput(it?.toString()?.trim() ?: "")
        }

        // 搜索按钮事件褚丽丽
        binding.btnSearch.setOnClickListener {
            val keyword = if (searchInput.text.toString().trim().isNotEmpty()) {
                searchInput.text.toString()
            } else {
                searchInput.hint.toString()
            }
            gotoSearchResult(keyword)
        }

        // 返回按钮事件处理
        binding.back.setOnClickListener {
            finish()
        }

        // 数据变化监听
        viewModel.tips.observe(this, true) {
            adapter.notifyDataSetChanged()
        }
    }

    fun gotoSearchResult(keyword: String) {
        startActivity(Intent(this, SearchResultActivity::class.java).apply {
            putExtra("search", keyword)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}