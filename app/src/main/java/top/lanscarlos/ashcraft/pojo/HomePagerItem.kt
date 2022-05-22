package top.lanscarlos.ashcraft.pojo

import android.graphics.Bitmap
import android.net.Uri
import top.lanscarlos.ashcraft.internet.GenericService.Companion.accessBitmap

class HomePagerItem(
    val uri: Uri
) {

    lateinit var bitmap: Bitmap
        private set

    fun requireBitmap(func: ((Bitmap) -> Unit)) {
        if (::bitmap.isInitialized) {
            func(bitmap)
        } else {
            uri.accessBitmap {
                bitmap = it
                func(it)
            }
        }
    }
}