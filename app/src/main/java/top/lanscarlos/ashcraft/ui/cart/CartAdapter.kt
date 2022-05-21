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

    val cart get() = AshCraftContext.cart
    val items get() = cart.items

    private var onItemClickListener: ((holder: ViewItem) -> Unit)? = null
    private var onItemSelectedListener: ((holder: ViewItem, selected: Boolean) -> Unit)? = null
    private var onItemAmountChangedListener: ((holder: ViewItem, amount: Int, isAdd: Boolean) -> Unit)? = null
    private var onItemRemovedListener: ((holder: ViewItem) -> Unit)? = null

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

    /**
     * 当选项数量被改变时
     * */
    fun setOnItemAmountChangedListener(func: ((holder: ViewItem, amount: Int, isAdd: Boolean) -> Unit)) {
        onItemAmountChangedListener = func
    }

    /**
     * 当选项数量被改变时
     * */
    fun setOnItemRemovedListener(func: ((holder: ViewItem) -> Unit)) {
        onItemRemovedListener = func
    }

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
                holder.selected.isChecked = viewModel.isSelectAll.value

                // 设置监听器
                onItemClickListener?.let { listener -> holder.itemView.setOnClickListener { listener(holder) } }

                /*
                * 处理复选框逻辑
                * */
                holder.selected.setOnCheckedChangeListener { _, selected ->
                    if (selected) {
                        cart.selected += 1
                        cart.totalPrice += items[index].totalPrice
                    } else {
                        cart.totalPrice -= items[index].totalPrice
                    }
                    onItemSelectedListener?.let { listener -> listener(holder, selected) }
                    if (!selected) cart.selected -= 1
                }

                /*
                * 处理商品数量增加逻辑
                * */
                holder.add.setOnClickListener {
                    items[index].amount += 1
                    items[index].totalPrice += items[index].price
                    if (holder.selected.isChecked) cart.totalPrice += items[index].price
                    onItemAmountChangedListener?.let { listener -> listener(holder, items[index].amount, true) }
                    holder.amount.text = items[index].amount.toString()
                }

                /*
                * 处理商品数量减少逻辑
                * */
                holder.sub.setOnClickListener {
                    if (items[index].amount <= 1) return@setOnClickListener
                    items[index].amount -= 1
                    items[index].totalPrice -= items[index].price
                    if (holder.selected.isChecked) cart.totalPrice -= items[index].price
                    onItemAmountChangedListener?.let { listener -> listener(holder, items[index].amount, false) }
                    holder.amount.text = items[index].amount.toString()
                }

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

    @Deprecated("Unsafe! Please Use forEach(func: ((Int, ViewItem) -> Unit)) instead!")
    fun forEach(func: ((holder: ViewItem) -> Unit)) {
        this.forEach { _, holder -> func(holder) }
    }

    inline fun forEach(func: ((index: Int, holder: ViewItem) -> Unit)) {
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

        var isRemoved = false
            private set

        fun remove() {
            if (selected.isChecked) {
                cart.selected -= 1
                cart.totalPrice -= items[bindingAdapterPosition].totalPrice
            }
            onItemRemovedListener?.let { listener -> listener(this) }
            cart.removeAt(bindingAdapterPosition)
            this@CartAdapter.notifyItemRemoved(bindingAdapterPosition)
            isRemoved = true
        }
    }

}