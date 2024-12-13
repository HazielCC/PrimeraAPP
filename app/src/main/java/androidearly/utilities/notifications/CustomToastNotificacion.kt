package androidearly.utilities.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.coco.primeraapp.R


private var lastToast: Toast? = null

// Muestra un Toast personalizado con un mensaje
fun Toast.showCustomToast(context: Context, message: String) {
    val inflater = LayoutInflater.from(context)
    val customToastView: View = inflater.inflate(R.layout.activity_custom_toast_notificacion, null)


    // Encuentra el TextView en tu layout personalizado y asigna el mensaje
    val textView = customToastView.findViewById<TextView>(R.id.tvLoading)
    textView.text = message

    this.view = customToastView
    this.duration = Toast.LENGTH_SHORT
    this.show()
}

// Muestra un Toast personalizado con un mensaje y un color en el borde
fun Context.showCustomToastNotification(
    message: String,
    color: Int = 0,
) {
    // Cancela el último Toast si aún se está mostrando
    lastToast?.cancel()

    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val customToastView: View = inflater.inflate(R.layout.activity_custom_toast_notificacion, null)

    // Encuentra el TextView en tu layout personalizado y asigna el mensaje
    val cvToast = customToastView.findViewById<CardView>(R.id.cvToast)
    val toastText = customToastView.findViewById<TextView>(R.id.tvLoading)
    val toastTitle = customToastView.findViewById<TextView>(R.id.toastTitle)
    val toastIcon = customToastView.findViewById<ImageView>(R.id.toastIcon)

    if (message.isEmpty()) {
        toastText.text = getString(R.string.error_servicio)
    } else {
        toastText.text = message
    }

    when (color) {
        // 0 -> Error
        0 -> {
            cvToast.setBackgroundResource(R.drawable.border_red)
            toastTitle.text = getString(R.string.error)
            toastIcon.setImageResource(R.drawable.ic_error)
        }

        // 1 -> Éxito
        1 -> {
            cvToast.setBackgroundResource(R.drawable.border_green)
            toastTitle.text = getString(R.string.exito)
            toastIcon.setImageResource(R.drawable.ic_success)
        }

        // 2 -> Advertencia
        2 -> {
            cvToast.setBackgroundResource(R.drawable.border_yellow)
            toastTitle.text = getString(R.string.advertencia)
            toastIcon.setImageResource(R.drawable.ic_warning)
        }

        // 3 -> Información
        3 -> {
            cvToast.setBackgroundResource(R.drawable.border_blue)
            toastTitle.text = getString(R.string.informacion)
            toastIcon.setImageResource(R.drawable.ic_information)

        }
    }

    val toast = Toast(this)
    toast.view = customToastView
    toast.duration = Toast.LENGTH_LONG

    // Guarda una referencia al Toast que acabas de crear
    lastToast = toast

    toast.show()
}
