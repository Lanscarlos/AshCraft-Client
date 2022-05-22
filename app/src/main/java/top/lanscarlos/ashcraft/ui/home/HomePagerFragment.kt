package top.lanscarlos.ashcraft.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.databinding.FragmentHomePagerBinding

class HomePagerFragment(val index: Int) : Fragment() {

    private var _binding: FragmentHomePagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePagerBinding.inflate(inflater, container, false)

        AshCraftContext.homePagers[index].requireBitmap {
            binding.image.setImageBitmap(it)
        }

        return binding.root
    }

}