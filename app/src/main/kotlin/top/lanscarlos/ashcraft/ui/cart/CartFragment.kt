package top.lanscarlos.ashcraft.ui.cart

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.databinding.FragmentCartBinding
import top.lanscarlos.ashcraft.model.CartViewModel
import top.lanscarlos.ashcraft.model.HomeViewModel
import kotlin.math.absoluteValue

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)

        val adapter = CartAdapter(binding.items, viewModel)
        binding.items.adapter = adapter
        // 添加侧滑删除逻辑
        ItemTouchHelper(SwipeCallback(binding.delete)).attachToRecyclerView(binding.items)

        /*
        * UI 相关
        * */

        val btnSelectAll = binding.selectAll
        val btnPurchase = binding.purchase
        val btnSwipeRefresh = binding.swipeRefresh
        val textTotalPrice = binding.totalPrice

        btnSelectAll.setOnCheckedChangeListener { _, selected ->
            viewModel.selectAll(selected)
        }

        viewModel.itemCount.observe(viewLifecycleOwner) {
            if (it <= 0) {
                btnSelectAll.isChecked = false
                btnSelectAll.isEnabled = false
            } else if (!btnSelectAll.isEnabled) {
                btnSelectAll.isEnabled = true
            }
        }

        viewModel.isAllSelected.observe(viewLifecycleOwner, false) {
            btnSelectAll.isChecked = it
        }

        viewModel.itemSelected.observe(viewLifecycleOwner, false) {
            adapter.forEach { _, holder ->
                holder.selected.isChecked = it
            }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            textTotalPrice.text = String.format("%.2f", it.absoluteValue)
        }

        /*
        * 下拉刷新逻辑处理
        * */
        btnSwipeRefresh.apply {
            // 设置下拉刷新按钮的颜色主题
            setColorSchemeColors(TypedValue().also {
                requireActivity().theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, it, true)
            }.data)
            setOnRefreshListener {
                adapter.notifyDataSetChanged()
                isRefreshing = false
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}