package top.lanscarlos.ashcraft.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.pojo.Commodity
import top.lanscarlos.ashcraft.pojo.Nameable
import top.lanscarlos.ashcraft.pojo.Shop
import java.lang.IllegalArgumentException

class SearchResultAdapter(
    type: Int= TYPE_ALL,
    commodities: List<Nameable>
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    companion object {
        val TYPE_ALL = 0
        val TYPE_ITEM = 1
        val TYPE_SHOP = 2
        private val TYPE_FOOTER = 3
    }

    val items = when (type) {
        TYPE_ALL -> commodities
        TYPE_ITEM -> commodities.filterIsInstance(Commodity::class.java)
        TYPE_SHOP -> commodities.filterIsInstance(Shop::class.java)
        else -> throw IllegalArgumentException("Unknown view type: $type")
    }

    override fun getItemViewType(index: Int): Int {
        return if (index == items.size) {
            TYPE_FOOTER
        } else if (items[index] is Commodity) {
            TYPE_ITEM
        } else if (items[index] is Shop) {
            TYPE_SHOP
        } else TYPE_FOOTER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
                ViewItem(view)
            }
            TYPE_SHOP -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_shop, parent, false)
                ViewShop(view)
            }
            TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.search_empty, parent, false)
                ViewFooter(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        when (holder) {
            is ViewItem -> {
                val item = items[index] as? Commodity ?: return
                holder.name.text = item.name
                holder.lore.text = item.lore
                holder.shop.text = item.shop.name
                holder.price.text = item.price.toString()
                holder.type.text = item.category
                item.image?.let { holder.image.setImageBitmap(it) }
            }
            is ViewShop -> {
                val item = items[index] as? Shop ?: return
                holder.name.text = item.name
                holder.lore.text = item.description
                holder.rating.rating = item.rating
                val tag = when (item.age) {
                    3 -> "三年老店"
                    4 -> "四年老店"
                    5 -> "五年老店"
                    6 -> "六年老店"
                    7 -> "七年老店"
                    8 -> "八年老店"
                    9 -> "九年老店"
                    10 -> "十年老店"
                    else -> null
                }
                holder.tag.text = tag ?: "新店开张"
                item.avatar?.let { holder.avatar.setImageBitmap(it) }
            }
        }
    }

    override fun getItemCount(): Int = if (items.isNotEmpty()) items.size else 1

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class ViewFooter(view: View): ViewHolder(view) {}

    inner class ViewItem(view: View) : ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.itemImage)
        val name = view.findViewById<TextView>(R.id.itemName)
        val lore = view.findViewById<TextView>(R.id.itemLore)
        val price = view.findViewById<TextView>(R.id.itemPrice)
        val shop = view.findViewById<TextView>(R.id.itemShop)
        val type = view.findViewById<TextView>(R.id.itemType)
    }

    inner class ViewShop(view: View) : ViewHolder(view) {
        val avatar = view.findViewById<ImageView>(R.id.shopAvatar)
        val name = view.findViewById<TextView>(R.id.shopName)
        val lore = view.findViewById<TextView>(R.id.shopDescription)
        val tag = view.findViewById<TextView>(R.id.shopTag)
        val rating = view.findViewById<RatingBar>(R.id.rating)
    }
}