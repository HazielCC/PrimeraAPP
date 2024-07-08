package fotosApp.vml

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class CameraViewModel : ViewModel() {
    // LiveData para notificar a la UI cuando se ha formateado con éxito una imagen
    private val onPictureSuccess = MutableLiveData<Bitmap>()

    // Función para obtener una imagen formateada
    fun getFormattedImage(pictureFile: File?): Bitmap? {
        // Si el archivo de la imagen no es nulo, redimensiona la imagen
        // Si es nulo, asigna null a bitmap
        val bitmap = if (pictureFile != null) {
            resizeBitmap(pictureFile)
        } else {
            null
        }
        // Actualiza el valor de onPictureSuccess con la imagen formateada
        onPictureSuccess.value = bitmap
        // Devuelve la imagen formateada
        return onPictureSuccess.value
    }

    // Función para redimensionar un bitmap
    private fun resizeBitmap(file: File): Bitmap {
        // Define los nuevos valores de ancho y alto
        val newWidth = 360
        val newHeight = 480
        // Decodifica el archivo de la imagen en un bitmap
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        // Obtiene el ancho y el alto del bitmap
        val width = bitmap.width
        val height = bitmap.height
        // Calcula las escalas de ancho y alto
        val scaleWidth = newWidth.toFloat() / width.toFloat()
        val scaleHeight = newHeight.toFloat() / height.toFloat()
        // Crea una nueva matriz
        val matrix = Matrix()
        // Rota la matriz 0 grados
        matrix.postRotate(0f)
        // Escala la matriz con las escalas de ancho y alto
        matrix.postScale(scaleWidth, scaleHeight)
        // Crea un nuevo bitmap con la matriz escalada
        val resizedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, width, height, matrix, false
        )
        // Si el bitmap redimensionado no es el mismo que el original, recicla el original
        if (resizedBitmap != bitmap) {
            bitmap.recycle()
        }
        // Devuelve el bitmap redimensionado
        return resizedBitmap
    }
}
