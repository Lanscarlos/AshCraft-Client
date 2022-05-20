package top.lanscarlos.ashcraft.ui.profile

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val maxOffset = binding.toolBar.height - binding.appBar.height
            viewModel.collapsed.value = maxOffset == verticalOffset
        })

        val profileToolbar = binding.profileToolbar.root
        viewModel.collapsed.observe(viewLifecycleOwner, false) { collapsed ->
            ObjectAnimator.ofFloat(profileToolbar, "alpha", if (collapsed) 1f else 0f).apply {
                duration = 100
            }.start()
        }

        binding.imgBackground.setImageResource(R.drawable.bitmap_profile_background)

        return binding.root
    }
}