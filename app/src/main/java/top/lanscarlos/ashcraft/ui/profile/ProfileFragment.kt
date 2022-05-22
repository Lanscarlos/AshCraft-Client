package top.lanscarlos.ashcraft.ui.profile

import android.animation.ObjectAnimator
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.FragmentProfileBinding
import top.lanscarlos.ashcraft.util.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val user get() = AshCraftContext.user

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
        viewModel.collapsed.observe(viewLifecycleOwner, true) { collapsed ->
            ObjectAnimator.ofFloat(profileToolbar, "alpha", if (collapsed) 1f else 0f).apply {
                duration = 100
            }.start()
        }

        binding.profileToolbar.name.text = user.name
        binding.profileHeader.name.text = user.name
        binding.profileHeader.signature.text = user.signature

        val uri = resources.openRawResource(R.raw.uri_avatar_def).parseUri()
        binding.profileHeader.avatar.setImageUriScheme(uri)
        binding.profileToolbar.avatar.setImageUriScheme(uri)

        return binding.root
    }
}