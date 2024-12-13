package androidearly.utilities.notifications

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityCustomToastNotificacionBinding

private var lastToast: Toast? = null

fun Context.showCustomToastNotification(
    message: String,
    color: Int = 0,
) {
    lastToast?.cancel()

    val inflater = LayoutInflater.from(this)
    val binding = ActivityCustomToastNotificacionBinding.inflate(inflater)

    if (message.isEmpty()) {
        binding.tvLoading.text = getString(R.string.error_servicio)
    } else {
        binding.tvLoading.text = message
    }

    when (color) {
        0 -> {
            binding.cvToast.setBackgroundResource(R.drawable.border_red)
            binding.toastTitle.text = getString(R.string.error)
            binding.toastIcon.setImageResource(R.drawable.ic_error)
        }

        1 -> {
            binding.cvToast.setBackgroundResource(R.drawable.border_green)
            binding.toastTitle.text = getString(R.string.exito)
            binding.toastIcon.setImageResource(R.drawable.ic_success)
        }

        2 -> {
            binding.cvToast.setBackgroundResource(R.drawable.border_yellow)
            binding.toastTitle.text = getString(R.string.advertencia)
            binding.toastIcon.setImageResource(R.drawable.ic_warning)
        }

        3 -> {
            binding.cvToast.setBackgroundResource(R.drawable.border_blue)
            binding.toastTitle.text = getString(R.string.informacion)
            binding.toastIcon.setImageResource(R.drawable.ic_information)
        }
    }

    val toast = Toast(this)
    toast.view = binding.root
    toast.duration = Toast.LENGTH_LONG

    lastToast = toast

    toast.show()
}