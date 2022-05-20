package top.lanscarlos.ashcraft.ui.cart

import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.util.Log
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeCallback(
    val delete: ImageView
) : ItemTouchHelper.Callback() {

    var swiping = false

    override fun isItemViewSwipeEnabled(): Boolean = !swiping

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
//        val swipeFlags = ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val holder = viewHolder as? CartAdapter.ViewHolder ?: return super.getSwipeThreshold(viewHolder)
//        return (delete.width.toFloat() / holder.itemView.width.toFloat()) * 1.5f
        return super.getSwipeThreshold(viewHolder)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val holder = viewHolder as? CartAdapter.ViewItem ?: return
        Log.d("Ash", "onSwiped...")
        holder.price.text = direction.toString()
        ObjectAnimator.ofFloat(delete, "alpha", 0f).apply {
            duration = 300
            swiping = true
            start()
        }.doOnEnd {
            holder.remove()
            delete.translationX = delete.width.toFloat()
            delete.alpha = 1f
            swiping = false
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val holder = viewHolder as? CartAdapter.ViewItem ?: return
        if (holder.isRemoved) return
        delete.translationY = holder.itemView.y + holder.itemView.height * 0.9f
        delete.translationX = dX + delete.width
        Log.d("Ash", "dX -> $dX")
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}