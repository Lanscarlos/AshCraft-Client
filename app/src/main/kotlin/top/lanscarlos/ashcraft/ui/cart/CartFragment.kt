package top.lanscarlos.ashcraft.ui.cart

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.FragmentCartBinding
import top.lanscarlos.ashcraft.model.CartViewModel
import top.lanscarlos.ashcraft.repository.CartRepository
import top.lanscarlos.ashcraft.repository.OrderRepository
import top.lanscarlos.ashcraft.repository.UserRepository
import top.lanscarlos.ashcraft.ui.order.OrderActivity
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

        btnSelectAll.isChecked = viewModel.isAllSelected.value

        viewModel.itemCount.observe(viewLifecycleOwner, true) {
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

        btnSelectAll.setOnCheckedChangeListener { _, selected ->
            viewModel.selectAll(selected)
        }

        viewModel.itemSelected.observe(viewLifecycleOwner, false) {
            adapter.forEach { _, holder ->
                holder.selected.isChecked = it
            }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner, true) {
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
                viewModel.refresh {
                    adapter.notifyDataSetChanged()
                    isRefreshing = false
                }
            }
        }

        CartRepository.setOnRefresh {
            viewModel.refresh {
                adapter.notifyDataSetChanged()
            }
        }

        binding.purchase.setOnClickListener {
            val dialog = showLoading()
            OrderRepository.tryPurchase {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    dialog.dismiss()
                    if (it != null) {
                        UserRepository.tryAutoLogin()
                        startActivity(Intent(requireContext(), OrderActivity::class.java).apply {
                            putExtra("orderId", it.id)
                        })
                    } else {
                        Toast.makeText(requireContext(), "购买失败！", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showLoading(): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.loading, null)

        val dialog = Dialog(requireContext()).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(view)
        }

        val window = dialog.window ?: return dialog
        val attributes = window.attributes
        attributes.width = 128
        attributes.height = 128
        window.attributes = attributes
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.show()

        return dialog
    }

}