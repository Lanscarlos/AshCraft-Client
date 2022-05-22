package top.lanscarlos.ashcraft.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.databinding.FragmentHomePagerBinding
import top.lanscarlos.ashcraft.util.AppService
import top.lanscarlos.ashcraft.util.buildService
import top.lanscarlos.ashcraft.util.enqueue

class HomePagerFragment(val index: Int) : Fragment() {

    private var _binding: FragmentHomePagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePagerBinding.inflate(inflater, container, false)

        buildService<AppService>().getImg(AshCraftContext.homePagers[index].image).enqueue { _, response ->
            val stream = response.body()?.byteStream()
            val bitmap = BitmapFactory.decodeStream(stream)
            binding.image.setImageBitmap(bitmap)
        }

        return binding.root
    }

}