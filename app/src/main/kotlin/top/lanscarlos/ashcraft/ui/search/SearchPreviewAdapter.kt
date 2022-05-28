package top.lanscarlos.ashcraft.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.model.SearchViewModel

class SearchPreviewAdapter(
    val activity: SearchPreviewActivity,
    val viewModel: SearchViewModel
) : RecyclerView.Adapter<SearchPreviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPreviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_preview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchPreviewAdapter.ViewHolder, index: Int) {
        val tips = viewModel.tips.value
        val input = viewModel.input.value
        val item = tips[index]
        holder.tipsHeader.text = input
        holder.tipsBody.text = item.substring(item.indexOf(input) + input.length)

        holder.itemView.setOnClickListener {
            activity.gotoSearchResult(item)
        }
    }

    override fun getItemCount(): Int = viewModel.tips.value.size

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipsHeader = view.findViewById<TextView>(R.id.tipsHeader)
        val tipsBody = view.findViewById<TextView>(R.id.tipsBody)
    }
}