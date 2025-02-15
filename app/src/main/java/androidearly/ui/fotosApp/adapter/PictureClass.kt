package androidearly.ui.fotosApp.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import java.io.File
import java.io.Serializable

class PictureClass(
    var file: File?,
    var uri: Uri?,
    var bitmap: Bitmap?
) : Serializable {

    fun getFormattedImage(): Bitmap? {
        return file?.let { resizeBitmap(it, 360, 480) }
    }

    private fun resizeBitmap(file: File, newWidth: Int, newHeight: Int): Bitmap {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val scaleWidth = newWidth.toFloat() / bitmap.width.toFloat()
        val scaleHeight = newHeight.toFloat() / bitmap.height.toFloat()

        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
            .also { resizedBitmap ->
                if (resizedBitmap != bitmap) {
                    bitmap.recycle()
                }
            }
    }
}