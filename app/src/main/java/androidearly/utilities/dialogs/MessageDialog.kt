package androidearly.utilities.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidearly.utilities.services.API.isNetworkAvailable
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityMessageDialogBinding

class MessageDialog {
    private lateinit var binding: ActivityMessageDialogBinding
    private var dialog: AlertDialog? = null

    private fun show(context: Context, message: String, type: String) {
        // Verificar si ya hay un diálogo visible
        if (dialog?.isShowing == true) {
            return
        }

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        binding = ActivityMessageDialogBinding.inflate(inflater)
        val view = binding.root
        builder.setView(view)
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent) // Hacer el fondo del diálogo transparente
        dialog?.show()

        binding.tvText.text = message

        when (type) {
            "error" -> {
                dialog?.setCancelable(false)
                binding.tvTitle.text = context.getString(R.string.error)
                binding.tvTitle.setTextColor(context.getColor(R.color.red))
                binding.ivIcon.setImageResource(R.drawable.ic_error)
            }

            "success" -> {
                binding.tvTitle.text = context.getString(R.string.exito)
                binding.tvTitle.setTextColor(context.getColor(R.color.green))
                binding.ivIcon.setImageResource(R.drawable.ic_success)
            }

            "info" -> {
                binding.tvTitle.text = context.getString(R.string.informacion)
                binding.tvTitle.setTextColor(context.getColor(R.color.blue))
                binding.ivIcon.setImageResource(R.drawable.ic_information)
            }

            "warning" -> {
                binding.tvTitle.text = context.getString(R.string.advertencia)
                binding.tvTitle.setTextColor(context.getColor(R.color.yellow))
                binding.ivIcon.setImageResource(R.drawable.ic_warning)
            }

            "error_internet" -> {
                dialog?.setCancelable(false)

                binding.tvTitle.text = context.getString(R.string.error_servicio)
                binding.tvTitle.setTextColor(context.getColor(R.color.red))
                binding.ivIcon.setImageResource(R.drawable.ic_error)

                binding.btnClose.visibility = View.GONE
                binding.btnAction.text = context.getString(R.string.reintentar)

                binding.btnAction.setOnClickListener {
                    context.isNetworkAvailable().let {
                        if (it) {
                            dialog?.dismiss()
                        } else {
                            dialog?.dismiss()
                            show(
                                context,
                                context.getString(R.string.error_internet),
                                "error_internet"
                            )
                        }
                    }
                }
            }
        }

        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    fun showErrorMessage(context: Context, message: String) {
        show(context, message, "error")
    }

    fun showSuccessMessage(context: Context, message: String) {
        show(context, message, "success")
    }

    fun showInfoMessage(context: Context, message: String) {
        show(context, message, "info")
    }

    fun showWarningMessage(context: Context, message: String) {
        show(context, message, "warning")
    }

    fun showCustomMessageWithAction(
        context: Context,
        title: String,
        message: String,
        type: Int = 1,
        action: String
    ) {
        when (type) {
            1 -> {
                show(context, message, "error")
                binding.btnAction.text = context.getString(R.string.reintentar)
            }

            2 -> {
                show(context, message, "success")
                binding.btnAction.text = context.getString(R.string.aceptar)
            }

            3 -> {
                show(context, message, "warning")
                binding.btnAction.text = context.getString(R.string.cancelar)
            }

            4 -> {
                show(context, message, "info")
                binding.btnAction.text = context.getString(R.string.continuar)
            }
        }
        binding.tvTitle.text = title
        binding.btnAction.text = action
    }

    fun showInternetErrorMessage(context: Context) {
        show(context, context.getString(R.string.error_internet), "error_internet")
    }
}
