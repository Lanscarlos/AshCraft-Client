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

        Log.d("Ash", "Profile create...")
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val maxOffset = binding.toolBar.height - binding.appBar.height
            Log.d("Ash", "maxOffset -> $maxOffset")
            Log.d("Ash", "verticalOffset -> $verticalOffset")
            profileViewModel.setCollapse(maxOffset == verticalOffset)
        })

        val profileToolbar = binding.profileToolbar.root
        profileViewModel.collapse.observe(viewLifecycleOwner) { collapsed ->
            ObjectAnimator.ofFloat(profileToolbar, "alpha", if (collapsed) 1f else 0f).apply {
                duration = 100
                start()
            }
        }

        binding.imgBackground.setImageResource(R.drawable.bitmap_profile_background)

        return root
    }
}