package top.lanscarlos.ashcraft.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityOrderBinding
import top.lanscarlos.ashcraft.databinding.ActivityOrderOverviewBinding
import top.lanscarlos.ashcraft.repository.OrderRepository
import java.lang.NullPointerException

class OrderActivity : AppCompatActivity() {

    private var _binding: ActivityOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(AshCraftContext.theme.value)
        _binding = ActivityOrderBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val orderId = intent?.getIntExtra("orderId", -1)
        val order = OrderRepository.orders.firstOrNull {
            orderId == it.id
        } ?: throw NullPointerException("OrderActivity: Cannot find order by id $orderId!")

        binding.totalPrice.text = String.format("%.2f", order.totalPrice)
        binding.orderId.text = order.id.toString()
        binding.purchaseTime.text = order.purchaseTime

        binding.items.adapter = OrderAdapter(order)
        // 添加自定义分隔线
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_search)!!)
        binding.items.addItemDecoration(divider)

        binding.back.setOnClickListener {
            finish()
        }

        binding.share.setOnClickListener {
            Toast.makeText(this, "分享功能开发中...敬请期待", Toast.LENGTH_SHORT).show()
        }
    }
}