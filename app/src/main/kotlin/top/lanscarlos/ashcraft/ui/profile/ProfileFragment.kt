package top.lanscarlos.ashcraft.ui.profile

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.FragmentProfileBinding
import top.lanscarlos.ashcraft.model.ProfileViewModel
import top.lanscarlos.ashcraft.ui.log.LoginActivity
import top.lanscarlos.ashcraft.util.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val user get() = viewModel.user.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

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

        val textMoney = binding.profileBody.money
        viewModel.money.observe(viewLifecycleOwner, true) {
            textMoney.text = String.format("%.2f", it)
        }

        binding.profileToolbar.name.text = user.name
        binding.profileHeader.name.text = user.name
        binding.profileHeader.signature.text = user.signature
//
//        val uri = resources.openRawResource(R.raw.uri_avatar_def).parseUri()
//        binding.profileHeader.avatar.setImageUriScheme(uri)
//        binding.profileToolbar.avatar.setImageUriScheme(uri)

        binding.profileHeader.avatar.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}