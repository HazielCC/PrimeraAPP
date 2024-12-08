package androidearly.utilities.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.coco.primeraapp.R


private var lastToast: Toast? = null

// Muestra un Toast personalizado con un mensaje
fun Toast.showCustomToast(context: Context, message: String) {
    val inflater = LayoutInflater.from(context)
    val customToastView: View = inflater.inflate(R.layout.activity_custom_toast_notificacion, null)


    // Encuentra el TextView en tu layout personalizado y asigna el mensaje
    val textView = customToastView.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    this.view = customToastView
    this.duration = Toast.LENGTH_SHORT
    this.show()
}

// Muestra un Toast personalizado con un mensaje y un color en el borde
fun Context.showCustomToastNotification(
    message: String,
    color: Int = R.color.colorPrimary,
) {
    // Cancela el último Toast si aún se está mostrando
    lastToast?.cancel()

    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val customToastView: View = inflater.inflate(R.layout.activity_custom_toast_notificacion, null)

    // Encuentra el TextView en tu layout personalizado y asigna el mensaje
    val textView = customToastView.findViewById<TextView>(R.id.toast_text)
    val buttonAccentBorder = customToastView.findViewById<FrameLayout>(R.id.buttonAccentBorder)
    val rlBorderColor = customToastView.findViewById<RelativeLayout>(R.id.rlBorderColor)
    if (message.isEmpty()) {
        textView.text = "Error en el servicio"
    } else {
        textView.text = message
    }

    when (color) {
        1 -> {
            buttonAccentBorder.setBackgroundResource(R.color.red)
            rlBorderColor.setBackgroundResource(R.color.redTransparent)
        }

        2 -> {
            buttonAccentBorder.setBackgroundResource(R.color.green)
            rlBorderColor.setBackgroundResource(R.color.greenTransparent)
        }

        3 -> {
            buttonAccentBorder.setBackgroundResource(R.color.btnYellow)
            rlBorderColor.setBackgroundResource(R.color.yellowTransparent)
        }
    }

    val toast = Toast(this)
    toast.view = customToastView
    toast.duration = Toast.LENGTH_SHORT

    // Guarda una referencia al Toast que acabas de crear
    lastToast = toast

    toast.show()
}
