package top.lanscarlos.ashcraft.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R

class SearchResultAdapter(
    val name: String
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = name
    }

    override fun getItemCount(): Int = 10

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.itemImage)
        val name = view.findViewById<TextView>(R.id.itemName)
        val lore = view.findViewById<TextView>(R.id.itemLore)
        val price = view.findViewById<TextView>(R.id.itemPrice)
        val shop = view.findViewById<TextView>(R.id.itemShop)
    }
}