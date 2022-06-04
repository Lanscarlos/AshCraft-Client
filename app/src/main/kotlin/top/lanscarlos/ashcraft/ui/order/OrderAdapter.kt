package top.lanscarlos.ashcraft.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.pojo.Order
import top.lanscarlos.ashcraft.repository.CommodityRepository

class OrderAdapter(
    val order: Order
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = order.items[position]
        holder.name.text = item.commodity.name
        holder.lore.text = item.commodity.lore
        holder.amount.text = item.amount.toString()
        holder.totalPrice.text = String.format("%.2f", item.totalPrice)

        CommodityRepository.commodities.firstOrNull {
            item.commodity.id == it.id
        }?.image?.let {
            holder.image.setImageBitmap(it)
        }
    }

    override fun getItemCount(): Int = order.items.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)
        val lore = view.findViewById<TextView>(R.id.lore)
        val amount = view.findViewById<TextView>(R.id.amount)
        val totalPrice = view.findViewById<TextView>(R.id.totalPrice)
        val image = view.findViewById<ImageView>(R.id.image)
    }
}