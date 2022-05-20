package top.lanscarlos.ashcraft.ui.cart

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import top.lanscarlos.ashcraft.databinding.FragmentCartBinding
import top.lanscarlos.ashcraft.pojo.Treasure

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        val items = mutableListOf(
            Treasure("大保健", "值得一试", 9.9, "TB1sv3LtYj1gK0jSZFuXXcrHpXa-424-255.png"),
            Treasure("我去你的", "哈哈哈哈", 3.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"),
            Treasure("鸡汤", "哈哈哈哈", 19.9, "TB1RkUFt.H1gK0jSZSyXXXtlpXa-424-255.png"),
        )
        val adapter = CartAdapter(items)
        binding.items.adapter = adapter

        val swipeHelper = SwipeCallback(binding.delete)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.items)

        binding.swipeRefresh.apply {
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