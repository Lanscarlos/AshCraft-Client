package top.lanscarlos.ashcraft.ui.order

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityOrderOverviewBinding
import top.lanscarlos.ashcraft.repository.OrderRepository

class OrderOverviewActivity : AppCompatActivity() {

    private var _binding: ActivityOrderOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(AshCraftContext.theme.value)
        _binding = ActivityOrderOverviewBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val dialog = showLoading()
        OrderRepository.tryAccessData {
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                dialog.dismiss()
                binding.items.adapter = OrderOverviewAdapter(this@OrderOverviewActivity)
            }
        }
    }

    fun showLoading(): Dialog {
        val view = LayoutInflater.from(this).inflate(R.layout.loading, null)

        val dialog = Dialog(this).apply {
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