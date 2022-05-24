package top.lanscarlos.ashcraft.model

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import top.lanscarlos.ashcraft.ui.home.HomePagerItem

class HomeViewModel : ViewModel() {
    val homePagers = mutableListOf(
        HomePagerItem("https://imgcps.jd.com/ling4/100026667910/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6e2/a0b459cd/cr/s/q.jpg".toUri()),
        HomePagerItem("https://imgcps.jd.com/ling4/10032300178906/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6f6/fcacabb1/cr/s/q.jpg".toUri()),
        HomePagerItem("https://imgcps.jd.com/ling4/10047704922891/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6f5/5b8bd51b/cr/s/q.jpg".toUri()),
        HomePagerItem("https://imgcps.jd.com/ling4/100008347222/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5f3a47329785549f6bc7a6ee/c9c2aa56/cr/s/q.jpg".toUri()),
    )
}