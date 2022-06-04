package top.lanscarlos.ashcraft.ui.order

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.repository.CommodityRepository
import top.lanscarlos.ashcraft.repository.OrderRepository
import java.lang.IllegalArgumentException

class OrderOverviewAdapter(
    val context: Context
) : RecyclerView.Adapter<OrderOverviewAdapter.ViewHolder>() {

    private val TYPE_ITEMS = 0
    private val TYPE_FOOTER = 1

    private val items get() = OrderRepository.orders.sortedByDescending { it.id }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) TYPE_FOOTER else TYPE_ITEMS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_ITEMS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.order_overview_item, parent, false)
                ViewItem(view)
            }
            TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.order_empty, parent, false)
                ViewFooter(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        when (holder) {
            is ViewItem -> {
                val item = items[index]
                val first = item.items.first()
                holder.time.text = "下单时间 ${item.purchaseTime} >"
                holder.title.text = first.commodity.name + if (item.items.size > 1) "等..." else " x ${first.amount}"
                holder.lore.text = "共 ${item.items.size} 件商品"
                holder.totalPrice.text = String.format("%.2f", item.totalPrice)

                // 设置图片
                CommodityRepository.commodities.firstOrNull {
                    first.commodity.id == it.id
                }?.image?.let {
                    holder.image.setImageBitmap(it)
                }

                holder.itemView.setOnClickListener {
                    context.startActivity(Intent(context, OrderActivity::class.java).apply {
                        putExtra("orderId", item.id)
                    })
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) items.size ?: 1 else 1
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class ViewFooter(view: View): ViewHolder(view) {}

    inner class ViewItem(view: View): ViewHolder(view) {
        val time = view.findViewById<TextView>(R.id.purchaseTime)
        val title = view.findViewById<TextView>(R.id.orderTitle)
        val lore = view.findViewById<TextView>(R.id.orderLore)
        val totalPrice = view.findViewById<TextView>(R.id.totalPrice)
        val image = view.findViewById<ImageView>(R.id.image)
    }
}