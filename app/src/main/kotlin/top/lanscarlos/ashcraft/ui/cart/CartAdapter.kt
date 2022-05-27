package top.lanscarlos.ashcraft.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.internet.BaseUrl
import top.lanscarlos.ashcraft.internet.GenericService.Companion.accessBitmap
import top.lanscarlos.ashcraft.model.CartViewModel
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class CartAdapter(
    val recyclerView: RecyclerView,
    val viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val TYPE_ITEMS = 0
    private val TYPE_FOOTER = 1

    private val items get() = viewModel.items

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
                val item = items[index]
                holder.name.text = item.name
                holder.price.text = item.price.toString()
                holder.selected.isChecked = viewModel.isAllSelected.value
                items[index].image.toUri().accessBitmap {
                    holder.image.setImageBitmap(it)
                }

                holder.selected.setOnCheckedChangeListener { _, selected ->
                    viewModel.selectItem(holder.index, selected)
                }

                holder.add.setOnClickListener {
                    holder.amount.text = viewModel.increaseItemAmount(holder.index).toString()
                }

                holder.sub.setOnClickListener {
                    holder.amount.text = viewModel.decreaseItemAmount(holder.index).toString()
                }
            }
//            is ViewFooter -> {}
        }
    }

    override fun getItemCount(): Int = if (items.isNotEmpty()) items.size else 1

    fun getItemHolder(index: Int): ViewItem? {
        if (items.isEmpty()) return null
        val layoutManager = recyclerView.layoutManager ?: return null
        return layoutManager.getChildAt(index)?.let { recyclerView.getChildViewHolder(it) } as? ViewItem
    }

    fun forEach(func: ((index: Int, holder: ViewItem) -> Unit)) {
        if (items.isEmpty()) return
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
        val amount = view.findViewById<TextView>(R.id.amount)
        val add = view.findViewById<ImageView>(R.id.add)
        val sub = view.findViewById<ImageView>(R.id.sub)

        val index get() = bindingAdapterPosition
        var isRemoved = false
            private set

        fun remove() {
            viewModel.removeItem(index)
            this@CartAdapter.notifyItemRemoved(index)
            isRemoved = true
        }
    }

}