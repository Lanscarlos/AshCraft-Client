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

        val adapter = SearchPreviewAdapter(viewModel)
        binding.items.adapter = adapter
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_search)!!)
        binding.items.addItemDecoration(divider)

        val searchInput = binding.searchInput
        searchInput.hint = viewModel.result.random()
        searchInput.addTextChangedListener {
            viewModel.setInput(it?.toString()?.trim() ?: "")
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java).apply {
                putExtra("search", searchInput.text.toString())
            })
        }

        viewModel.tips.observe(this, true) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}