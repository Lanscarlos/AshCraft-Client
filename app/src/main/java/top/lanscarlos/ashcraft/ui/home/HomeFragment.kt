package top.lanscarlos.ashcraft.ui.home

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import top.lanscarlos.ashcraft.databinding.FragmentHomeBinding
import java.lang.Exception

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var cycling = false
    private var delay = 5000L
    private val cycleHandler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var cycleTask: Runnable

    init {
        cycleTask = Runnable {
            if (!cycling) return@Runnable
            val viewPager = binding.viewPager
            viewPager.setCurrentItemByAnimator(viewPager.currentItem + 1)
            cycleHandler.postDelayed(cycleTask, delay)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewPager = binding.viewPager
        val adapter = HomePagerAdapter(this)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(1, false)
        binding.indicator.apply {
            setupWithViewPager(viewPager)
            setHomePagerAdapter(adapter)
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            var currentPosition = 0

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state != ViewPager2.SCROLL_STATE_IDLE) return
                when (currentPosition) {
                    0 -> {
                        viewPager.setCurrentItem(adapter.itemCount - 2, false)
                    }
                    adapter.itemCount - 1 -> {
                        viewPager.setCurrentItem(1, false)
                    }
                }
            }
        })

        startCycle()

        return binding.root
    }

    /**
     * 开启自动轮播功能
     * */
    fun startCycle(delay: Long = this.delay) {
        if (cycling || delay <= 0) return
        cycling = true
        this.delay = delay
        cycleHandler.postDelayed(cycleTask, delay)
    }

    fun stopCycle() {
        cycling = false
    }

    /**
     * 根据给定时间动态切换页面
     *
     * @author https://blog.csdn.net/HBK_MySummerCT/article/details/104534851
     * */
    fun ViewPager2.setCurrentItemByAnimator(
        item: Int,
        duration: Long = 500L,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width // 使用viewpager2.getWidth()获取
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { }
            override fun onAnimationRepeat(animation: Animator?) { }
        })
        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }

}