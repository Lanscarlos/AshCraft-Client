package top.lanscarlos.ashcraft.ui.log

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityLoginBinding
import top.lanscarlos.ashcraft.databinding.ActivityRegisterBinding
import top.lanscarlos.ashcraft.repository.UserRepository

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivityRegisterBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val phone = binding.inputPhone
            val name = binding.inputName
            val password = binding.inputPassword
            val confirmPassword = binding.confirmPassword

            when {
                phone.text.toString().isEmpty() -> {
                    "请输入手机号".showToast()
                    return@setOnClickListener
                }
                name.text.toString().isEmpty() -> {
                    "请输入昵称".showToast()
                    return@setOnClickListener
                }
                password.text.toString().isEmpty() || confirmPassword.text.toString().isEmpty() -> {
                    "请输入密码".showToast()
                    return@setOnClickListener
                }
                password.text.toString() != confirmPassword.text.toString() -> {
                    "两次输入的密码不一致".showToast()
                    return@setOnClickListener
                }
                !binding.checkBox.isChecked -> {
                    "请先同意并勾选服务协议".showToast()
                    return@setOnClickListener
                }
            }

            val dialog = showLoading()
            UserRepository.tryRegister(phone.text.toString(), name.text.toString(), password.text.toString()) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    if (dialog.isShowing) dialog.dismiss()
                    if (it == null) {
                        "注册失败！请重新填写信息！".showToast()
                        return@launch
                    }
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

    }

    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this@RegisterActivity, this, duration).show()
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