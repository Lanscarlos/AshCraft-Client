package top.lanscarlos.ashcraft.ui.profile

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import top.lanscarlos.ashcraft.databinding.FragmentProfileBinding
import top.lanscarlos.ashcraft.model.ProfileViewModel
import top.lanscarlos.ashcraft.repository.UserRepository
import top.lanscarlos.ashcraft.ui.log.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val user get() = viewModel.user

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
        viewModel.money.observe(viewLifecycleOwner, false) {
            textMoney.text = String.format("%.2f", it)
        }

        val boot = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            "登录成功！".showToast()
            refresh()
        }

        binding.profileHeader.avatar.setOnClickListener {
            if (user != null) return@setOnClickListener
            boot.launch(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.profileBody.logout.setOnClickListener {
            if (user == null) return@setOnClickListener
            UserRepository.logout()
            refresh()
        }

        refresh()

        return binding.root
    }

    fun refresh() {
        binding.profileToolbar.name.text = user?.name ?: "请登录"
        binding.profileHeader.name.text = user?.name ?: "请登录"
        binding.profileHeader.signature.text = user?.signature ?: "登录即可享受特权"
        binding.profileBody.money.text = String.format("%.2f", user?.money ?: 0.0)
    }

    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), this, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}