package top.lanscarlos.ashcraft.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.pojo.Commodity
import top.lanscarlos.ashcraft.pojo.Nameable
import java.lang.IllegalArgumentException

class SearchResultFragment(
    index: Int,
    val result: List<Nameable>
) : Fragment() {

    val type = when (index) {
        0 -> SearchResultAdapter.TYPE_ALL
        1 -> SearchResultAdapter.TYPE_ITEM
        2 -> SearchResultAdapter.TYPE_SHOP
        else -> throw ArrayIndexOutOfBoundsException("SearchResultFragment index must between 0 to 2, but $index!")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.search_pager, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.items)
        val adapter = SearchResultAdapter(type, result)
        recyclerView.adapter = adapter
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_search)!!)
        recyclerView.addItemDecoration(divider)
        return view
    }

}