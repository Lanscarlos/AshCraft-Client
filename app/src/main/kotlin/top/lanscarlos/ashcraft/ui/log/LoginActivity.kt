package top.lanscarlos.ashcraft.ui.log

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityLoginBinding
import top.lanscarlos.ashcraft.repository.UserRepository

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivityLoginBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        UserRepository.rememberLog?.let {
            binding.inputPhone.setText(it.first)
            binding.inputPassword.setText(it.second)
        }

        val boot = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != RESULT_OK) return@registerForActivityResult
            "注册成功！".showToast()
            UserRepository.rememberLog?.let { log ->
                binding.inputPhone.setText(log.first)
                binding.inputPassword.setText(log.second)
            }
        }

        binding.gotoRegister.setOnClickListener {
            boot.launch(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if (!binding.checkBox.isChecked) {
                "请先同意并勾选服务协议".showToast()
                return@setOnClickListener
            }
            val dialog = showLoading()
            UserRepository.tryLogin(binding.inputPhone.text.toString(), binding.inputPassword.text.toString()) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    dialog.dismiss()
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

    }

    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this@LoginActivity, this, duration).show()
    }

    fun showLoading(): Dialog {
        val view = LayoutInflater.from(this).inflate(R.layout.loading, null)

        val dialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(view)
        }

        val window = dialog.window ?: return dialog
        val attributes = window.attributes
        attributes.width = 128
        attributes.height = 128
        window.attributes = attributes
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.show()

        return dialog
    }
}