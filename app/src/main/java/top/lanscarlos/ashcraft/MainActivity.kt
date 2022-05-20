package top.lanscarlos.ashcraft

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.coroutines.*
import top.lanscarlos.ashcraft.craft.CraftFragmentNavigator
import top.lanscarlos.ashcraft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var recreateFix = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this) {
            if (recreateFix) recreate() else recreateFix = true
        }
        setTheme(AshCraftContext.theme.value!!)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // 自定义 navigator
        val navigator = CraftFragmentNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment_activity_main)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.mobile_navigation)

        // 关联底部导航栏
        navView.setupWithNavController(navController)

        /*
        * 覆盖长按 Tooltip 信息
        * */
        navController.addOnDestinationChangedListener { _, _, _ ->
            // 使用协程延迟覆盖长按事件处理器
            CoroutineScope(Dispatchers.Main).launch {
                delay(10)
                (binding.navView.getChildAt(0) as BottomNavigationMenuView).forEach {
                    it.setOnLongClickListener { true }
                }
            }
        }
    }

}