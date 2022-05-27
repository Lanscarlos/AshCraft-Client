package top.lanscarlos.ashcraft.ui.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityLoginBinding
import top.lanscarlos.ashcraft.databinding.ActivityRegisterBinding

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


    }
}