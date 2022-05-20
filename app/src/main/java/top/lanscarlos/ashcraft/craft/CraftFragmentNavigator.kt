package top.lanscarlos.ashcraft.craft

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("craft_fragment")
class CraftFragmentNavigator(
    val context: Context,
    val manager: FragmentManager,
    val containerId: Int,
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        val transaction = manager.beginTransaction()

        // 动画处理
        val enterAnim = navOptions?.enterAnim ?: -1
        val exitAnim = navOptions?.exitAnim ?: -1
        val popEnterAnim = navOptions?.popEnterAnim ?: -1
        val popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            transaction.setCustomAnimations(
                if (enterAnim > -1) enterAnim else 0,
                if (exitAnim > -1) exitAnim else 0,
                if (popEnterAnim > -1) popEnterAnim else 0,
                if (popExitAnim > -1) popExitAnim else 0
            )
        }

        val initNav = manager.primaryNavigationFragment?.let {
//            transaction.detach(it)
            transaction.hide(it)
            false
        } ?: true

        val tag = destination.id.toString()
        val fragment = manager.findFragmentByTag(tag)?.also {
//            transaction.attach(it)
            transaction.show(it)
        } ?: let {
            val className = destination.className.let {
                if (it[0] == '.') context.packageName + it else it
            }
//            manager.fragmentFactory.instantiate(context.classLoader, className)
            instantiateFragment(context, manager, className, args).also {
                transaction.add(containerId, it, tag)
            }
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()
        return if (initNav) destination else null
    }
}