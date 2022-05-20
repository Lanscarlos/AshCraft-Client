package top.lanscarlos.ashcraft.ui.cart

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.util.AppService
import top.lanscarlos.ashcraft.util.buildService
import top.lanscarlos.ashcraft.util.enqueue
import java.lang.IllegalArgumentException

class CartAdapter(
    val items: MutableList<Treasure>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    val TYPE_ITEMS = 0
    val TYPE_FOOTER = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) TYPE_FOOTER else TYPE_ITEMS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_ITEMS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
                ViewItem(view)
            }
            TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_empty, parent, false)
                ViewFooter(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        when (holder) {
            is ViewItem -> {
                holder.name.text = items[index].name
                holder.price.text = items[index].price.toString()
                buildService<AppService>().getImg(items[index].img).enqueue { _, response ->
                    val stream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(stream)
                    holder.image.setImageBitmap(bitmap)
                    Log.d("Ash", "Image Response!")
                }
            }
//            is ViewFooter -> {}
        }
    }

    override fun getItemCount(): Int = if (items.isNotEmpty()) items.size else 1

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class ViewFooter(view: View): ViewHolder(view) {}

    inner class ViewItem(view: View): ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val name = view.findViewById<TextView>(R.id.name)

        val price = view.findViewById<TextView>(R.id.price)

        var isRemoved = false
            private set

        fun remove() {
            this@CartAdapter.items.removeAt(bindingAdapterPosition)
            this@CartAdapter.notifyItemRemoved(bindingAdapterPosition)
            isRemoved = true
        }
    }

}