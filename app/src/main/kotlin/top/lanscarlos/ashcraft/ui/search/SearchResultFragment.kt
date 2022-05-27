package top.lanscarlos.ashcraft.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import top.lanscarlos.ashcraft.R

class SearchResultFragment(val name: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.search_pager, container, false)

        val adapter = SearchResultAdapter(name)
        view.findViewById<RecyclerView>(R.id.items).adapter = adapter
        return view
    }

}