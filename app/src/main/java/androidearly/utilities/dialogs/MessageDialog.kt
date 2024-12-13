package androidearly.utilities.dialogs.messageDialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityMessageDialogBinding

class MessageDialog {
    private lateinit var binding: ActivityMessageDialogBinding
    private lateinit var dialog: AlertDialog

    fun show(context: Context, message: String, type: String) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        binding = ActivityMessageDialogBinding.inflate(inflater)
        val view = binding.root
        builder.setView(view)
        dialog = builder.create()
        dialog.show()

        binding.tvText.text = message

        when (type) {
            "error" -> {
                binding.tvTitle.text = context.getString(R.string.error)
                binding.tvTitle.setTextColor(context.getColor(R.color.red))
                binding.ivIcon.setImageResource(R.drawable.ic_error)
            }

            "error_internet" -> {
                binding.tvTitle.text = context.getString(R.string.error_servicio)
                binding.tvTitle.setTextColor(context.getColor(R.color.red))
                binding.ivIcon.setImageResource(R.drawable.ic_error)
            }

            "success" -> {
                binding.tvTitle.text = context.getString(R.string.exito)
                binding.tvTitle.setTextColor(context.getColor(R.color.green))
                binding.ivIcon.setImageResource(R.drawable.ic_success)
            }
        }

        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }
}
