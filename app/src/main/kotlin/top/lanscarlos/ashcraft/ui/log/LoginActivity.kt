package top.lanscarlos.ashcraft.ui.log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityLoginBinding
import top.lanscarlos.ashcraft.databinding.ActivitySearchBinding

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

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}