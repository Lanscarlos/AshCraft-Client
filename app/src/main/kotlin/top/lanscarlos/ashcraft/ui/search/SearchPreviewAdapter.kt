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
    val viewModel: SearchViewModel
) : RecyclerView.Adapter<SearchPreviewAdapter.ViewHolder>() {

//    val input get() = viewModel.input.value
//    val tips get() = viewModel.tips.value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPreviewAdapter.ViewHolder {
        Log.d("Ash", "onCreateViewHolder viewType -> $viewType")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_preview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchPreviewAdapter.ViewHolder, index: Int) {
        Log.d("Ash", "onBindViewHolder -> $index")
        val tips = viewModel.tips.value
        val input = viewModel.input.value
        val item = tips[index]
        holder.tipsHeader.text = input
        holder.tipsBody.text = item.substring(item.indexOf(input) + input.length)
    }

    override fun getItemCount(): Int {
        return viewModel.tips.value.size.also { Log.d("Ash", "size -> $it") }
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipsHeader = view.findViewById<TextView>(R.id.tipsHeader)
        val tipsBody = view.findViewById<TextView>(R.id.tipsBody)
    }
}