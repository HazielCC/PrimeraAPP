package androidearly.utilities.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityDialogBinding

class LoadingDialog {

    private var dialog: AlertDialog? = null
    private lateinit var binding: ActivityDialogBinding

    fun show(context: Context, message: String) {
        // Verificar si ya hay un diálogo visible
        if (dialog?.isShowing == true) {
            return
        }

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        binding = ActivityDialogBinding.inflate(inflater)
        val view = binding.root
        builder.setView(view)
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent) // Hacer el fondo del diálogo transparente

        // Load the GIF using Glide
        val gifImageView = binding.gifImageView
        Glide.with(context)
            .load(R.drawable.loading) // Replace 'sample_gif' with your GIF file name
            .into(gifImageView)
        binding.tvLoading.text = message

        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}
