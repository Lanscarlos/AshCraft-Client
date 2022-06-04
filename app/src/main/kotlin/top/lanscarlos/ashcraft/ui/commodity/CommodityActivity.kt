package top.lanscarlos.ashcraft.ui.commodity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.lanscarlos.ashcraft.AshCraftContext
import top.lanscarlos.ashcraft.R
import top.lanscarlos.ashcraft.databinding.ActivityCommodityBinding
import top.lanscarlos.ashcraft.databinding.ActivityLoginBinding
import top.lanscarlos.ashcraft.repository.CartRepository
import top.lanscarlos.ashcraft.repository.CommodityRepository
import top.lanscarlos.ashcraft.repository.ShopRepository
import java.lang.NullPointerException

class CommodityActivity : AppCompatActivity() {

    private var _binding: ActivityCommodityBinding? = null
    private val binding get() = _binding!!

    var like = false
    var star = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AshCraftContext.theme.observe(this, false) {
            recreate()
        }
        setTheme(AshCraftContext.theme.value)

        _binding = ActivityCommodityBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        // 图片滚动缩放逻辑
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val scale = 1f + scrollY.toFloat() * 0.0001f
            binding.image.scaleX = scale
            binding.image.scaleY = scale
            binding.image.translationY = - scrollY.toFloat() * 0.3f
        }


        // 返回键逻辑
        binding.back.setOnClickListener {
            finish()
        }

        // 商品分享功能
        binding.commodityShare.setOnClickListener {
            "分享功能开发中...敬请期待！".showToast()
        }

        // 查看店铺详情功能
        binding.store.setOnClickListener {
            "店铺功能开发中...敬请期待！".showToast()
        }

        // 查看店铺详情功能
        binding.comments.setOnClickListener {
            "评论功能开发中...敬请期待！".showToast()
        }

        // 设置点赞逻辑
        binding.like.setOnClickListener {
            like = !like
            binding.like.setImageResource(
                if (like) {
                    "get！商品也许更多人能看见！".showToast()
                    R.drawable.icon_good_fill
                } else {
                    "取消点赞成功".showToast()
                    R.drawable.icon_good
                }
            )
        }

        // 设置收藏逻辑
        binding.star.setOnClickListener {
            star = !star
            binding.star.setImageResource(
                if (star) {
                    "收藏成功".showToast()
                    R.drawable.icon_collection_fill
                } else {
                    "取消收藏成功".showToast()
                    R.drawable.icon_collection
                }
            )
        }

        val commodity = intent?.getIntExtra("commodityId", -1).let { id ->
            CommodityRepository.commodities.firstOrNull { it.id == id }
        } ?: throw NullPointerException("CommodityActivity commodity must not be null!")

        // 设置图片
        commodity.image?.let { binding.image.setImageBitmap(it) }

        binding.commodityTitle.text = commodity.name
        binding.commodityName.text = commodity.name
        binding.commodityLore.text = commodity.lore
        binding.commodityTag.text = commodity.category
        binding.commodityPrice.text = commodity.price.toString()
        binding.commodityStorage.text = commodity.storage.toString()

        binding.addToCart.setOnClickListener {
            val dialog = showLoading()
            CartRepository.addItem(commodity) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    dialog.dismiss()
                    "添加成功！快去购物车看看吧！".showToast()
                }
            }
        }

        val shop = ShopRepository.shops.firstOrNull { it.id == commodity.shop.id } ?: return

        shop.avatar?.let { binding.shopAvatar.setImageBitmap(it) }
        binding.shopName.text = shop.name
        binding.shopLore.text = shop.description
        binding.rating.rating = shop.rating
        val tag = when (shop.age) {
            3 -> "三年老店"
            4 -> "四年老店"
            5 -> "五年老店"
            6 -> "六年老店"
            7 -> "七年老店"
            8 -> "八年老店"
            9 -> "九年老店"
            10 -> "十年老店"
            else -> null
        }
        binding.shopTag.text = tag ?: "新店开张"

        // 自动弹出详情
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            // 属性动画
            ValueAnimator.ofInt(0, 250).apply {
                duration = 400
                addUpdateListener {
                    binding.nestedScrollView.smoothScrollTo(0, it.animatedValue as Int)
                }
            }.start()
        }

    }

    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this@CommodityActivity, this, duration).show()
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