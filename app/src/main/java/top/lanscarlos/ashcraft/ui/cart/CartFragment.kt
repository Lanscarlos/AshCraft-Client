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
import kotlin.math.absoluteValue

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private var _viewModel: CartViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    private val cart get() = AshCraftContext.cart
    private val items get() = cart.items

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this).get(CartViewModel::class.java)


        cart.addItem(Treasure("折叠板凳", "值得一试", 9.9, "TB1sv3LtYj1gK0jSZFuXXcrHpXa-424-255.png"))
        cart.addItem(Treasure("这是啥？", "哈哈哈哈", 99.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"))
//        cart.addItem(Treasure("鸡汤", "哈哈哈哈", 19.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"))

        val adapter = CartAdapter(binding.items, viewModel)

        // 设置数量监听器
        adapter.setOnItemAmountChangedListener { _, _, _ -> updateTotalPrice() }
        // 设置删除监听器
        adapter.setOnItemRemovedListener { updateTotalPrice() }

        /*
        * 处理子选项复选框逻辑
        * */
        adapter.setOnItemSelectedListener { _, selected ->

            // 防止全选按钮与子选按钮相互交织冲突
            if (viewModel.isSelecting.value) return@setOnItemSelectedListener

            /*
            * 处理与全选框的逻辑
            *
            * 检查是否已全选
            * 若其他 holder 全选且当前 selected 未选，则取消全选按钮
            * 若其他 holder 全选且当前 selected 已选，则启用全选按钮
            * */
            updateTotalPrice()
            if (cart.isAllSelect()) {
                // 静默设置全选逻辑
                viewModel.isSelectAll.setValueSilent(selected)
                binding.selectAll.isChecked = selected
            }
        }

        /*
        * 处理全选框逻辑
        * */
        binding.selectAll.setOnCheckedChangeListener { btn, selected ->
            viewModel.isSelectAll.value = selected
        }

        viewModel.isSelectAll.observe(viewLifecycleOwner, false) { selected ->
            viewModel.isSelecting.value = true
            // 改变子项所有全选按钮 并 重新计算总价
            var totalPrice = 0.0
            val items = AshCraftContext.cart.items
            adapter.forEach { index, holder ->
                holder.selected.isChecked = selected
                // 处理总价逻辑
                if (selected) totalPrice += items[index].totalPrice
            }
            cart.totalPrice = totalPrice
            updateTotalPrice()
            viewModel.isSelecting.value = false
        }
        binding.items.adapter = adapter
        // 添加侧滑删除逻辑
        val swipeHelper = SwipeCallback(binding.delete)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.items)

        /*
         * 监听购物车列表变化
         * */
        AshCraftContext.cart.addOnItemsSizeChanged { size, isAdd ->
            val button = binding.selectAll
            if (isAdd) return@addOnItemsSizeChanged
            if (size <= 0) {
                // 购物车被清空 禁用全选按钮
                button.isChecked = false
                button.isEnabled = false
                viewModel.isSelectAll.value = false
            } else if (!button.isEnabled) {
                // 解除全选按钮的禁用
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

    fun updateTotalPrice() {
        binding.totalPrice.text = String.format("%.2f", cart.totalPrice.absoluteValue)
    }

}