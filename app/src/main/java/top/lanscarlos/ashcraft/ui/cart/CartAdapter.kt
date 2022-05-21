package top.lanscarlos.ashcraft.ui.cart

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.pojo.Treasure
import top.lanscarlos.ashcraft.util.AppService
import top.lanscarlos.ashcraft.util.buildService
import top.lanscarlos.ashcraft.util.enqueue
import java.lang.IllegalArgumentException

class CartAdapter(
    val recyclerView: RecyclerView,
    val viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    val TYPE_ITEMS = 0
    val TYPE_FOOTER = 1

    private var onItemClickListener: ((holder: ViewItem) -> Unit)? = null
    private var onItemSelectedListener: ((holder: ViewItem, selected: Boolean) -> Unit)? = null

    /**
     * 当整个子选项被点击时触发
     * */
    fun setOnItemClickListener(func: ((holder: ViewItem) -> Unit)) {
        onItemClickListener = func
    }

    /**
     * 当复选框被点击时触发
     * */
    fun setOnItemSelectedListener(func: ((holder: ViewItem, selected: Boolean) -> Unit)) {
        onItemSelectedListener = func
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == AshCraftContext.items.size) TYPE_FOOTER else TYPE_ITEMS
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
                holder.name.text = AshCraftContext.items[index].name
                holder.price.text = AshCraftContext.items[index].price.toString()
                holder.selected.isChecked = viewModel.isSelectAll.value

                // 设置监听器
                onItemSelectedListener?.let { listener -> holder.selected.setOnCheckedChangeListener { _, selected -> listener(holder, selected) } }
                onItemClickListener?.let { listener -> holder.itemView.setOnClickListener { listener(holder) } }

                buildService<AppService>().getImg(AshCraftContext.items[index].img).enqueue { _, response ->
                    val stream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(stream)
                    holder.image.setImageBitmap(bitmap)
                    Log.d("Ash", "Image Response!")
                }
            }
//            is ViewFooter -> {}
        }
    }

    override fun getItemCount(): Int = if (AshCraftContext.items.isNotEmpty()) AshCraftContext.items.size else 1

    fun isAllSelected(): Boolean {
        forEach { _, holder ->
            if (!holder.selected.isChecked) return false
        }
        return true
    }

    fun forEach(func: ((holder: ViewItem) -> Unit)) {
        this.forEach { _, holder -> func(holder) }
    }

    inline fun forEach(func: ((index: Int, holder: ViewItem) -> Unit)) {
        if (AshCraftContext.items.isEmpty()) return
        val layoutManager = recyclerView.layoutManager ?: return
        for (i in 0 until layoutManager.childCount) {
            val holder = layoutManager.getChildAt(i)?.let { recyclerView.getChildViewHolder(it) }
            func(i, holder as? ViewItem ?: continue)
        }
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class ViewFooter(view: View): ViewHolder(view) {}

    inner class ViewItem(view: View): ViewHolder(view) {
        val selected = view.findViewById<CheckBox>(R.id.selected)
        val image = view.findViewById<ImageView>(R.id.image)
        val name = view.findViewById<TextView>(R.id.name)
        val price = view.findViewById<TextView>(R.id.price)

        var isRemoved = false
            private set

        fun remove() {
            AshCraftContext.removeAt(bindingAdapterPosition)
            this@CartAdapter.notifyItemRemoved(bindingAdapterPosition)
            isRemoved = true
        }
    }

}