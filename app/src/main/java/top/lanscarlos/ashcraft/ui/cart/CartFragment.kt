package top.lanscarlos.ashcraft.ui.cart

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.FragmentCartBinding
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.ui.profile.ProfileViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private var _viewModel: CartViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this).get(CartViewModel::class.java)


        AshCraftContext.addItem(Treasure("大保健", "值得一试", 9.9, "TB1sv3LtYj1gK0jSZFuXXcrHpXa-424-255.png"))
        AshCraftContext.addItem(Treasure("我去你的", "哈哈哈哈", 3.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"))
        AshCraftContext.addItem(Treasure("鸡汤", "哈哈哈哈", 19.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"))

        val adapter = CartAdapter(binding.items, viewModel)

        /*
        * 处理子选项复选框逻辑
        * */
        adapter.setOnItemSelectedListener { holder, selected ->
            if (viewModel.isSelecting.value) return@setOnItemSelectedListener
            /*
            * 检查是否已全选
            *
            * 若其他 holder 全选且当前 selected 未选，则取消全选按钮
            * 若其他 holder 全选且当前 selected 已选，则启用全选按钮
            * */
            adapter.forEach { _, it ->
                if (holder != it && !it.selected.isChecked) return@setOnItemSelectedListener
            }
            // 静默设置全选逻辑
            viewModel.isSelectAll.setValueSilent(selected)
            binding.selectAll.isChecked = selected
        }

        /*
        * 处理全选框逻辑
        * */
        binding.selectAll.setOnCheckedChangeListener { btn, selected ->
            viewModel.isSelectAll.value = selected
        }

        viewModel.isSelectAll.observe(viewLifecycleOwner, false) {
            viewModel.isSelecting.value = true
            // 改变子项所有全选按钮
            adapter.forEach { holder ->
                holder.selected.isChecked = it
            }
            viewModel.isSelecting.value = false
        }
        binding.items.adapter = adapter
        // 添加侧滑删除逻辑
        val swipeHelper = SwipeCallback(binding.delete)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.items)

        /*
         * 监听购物车列表变化
         * */
        AshCraftContext.addOnItemsSizeChanged { size, isAdd ->
            val button = binding.selectAll
            if (isAdd) return@addOnItemsSizeChanged
            if (size <= 0) {
                button.isChecked = false
                button.isEnabled = false
                viewModel.isSelectAll.value = false
            } else if (!button.isEnabled) {
                button.isEnabled = true
            }
        }

        /*
        * 下拉刷新逻辑处理
        * */
        binding.swipeRefresh.apply {
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