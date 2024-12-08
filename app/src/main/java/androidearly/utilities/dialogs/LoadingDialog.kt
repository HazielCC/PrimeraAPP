package androidearly.utilities.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityDialogBinding

class LoadingDialog : DialogFragment() {

    private var _binding: ActivityDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load the GIF using Glide
        val gifImageView = binding.gifImageView
        Glide.with(this)
            .load(R.drawable.loading) // Replace 'sample_gif' with your GIF file name
            .into(gifImageView)

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.setCanceledOnTouchOutside(false)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "loadingDialog"

        fun showLoadingDialog(activity: FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            val prevFragment = fragmentManager.findFragmentByTag(TAG)
            if (prevFragment != null) {
                fragmentManager.beginTransaction().remove(prevFragment).commitAllowingStateLoss()
            }
            val loadingDialog = LoadingDialog()
            loadingDialog.show(fragmentManager, TAG)
        }

        fun dismissLoadingDialog(activity: FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            val loadingDialog = fragmentManager.findFragmentByTag(TAG) as? LoadingDialog
            loadingDialog?.dismiss()
        }
    }
}
