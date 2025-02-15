package androidearly.ui.fotosApp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidearly.ui.fotosApp.adapter.PictureClass
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R


class CameraAdapter(
    private var items: ArrayList<PictureClass>,
    private var actions: SelectionDialogActions
) : RecyclerView.Adapter<CameraAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val picture: ImageView = itemView.findViewById(R.id.ivPictures)

        fun bind(item: PictureClass, actions: SelectionDialogActions) {
            item.bitmap?.let {
                picture.setImageBitmap(it)
            }
            picture.setOnClickListener {
                actions.onSelected(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_camara, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], actions)
        Log.d("ITEM", items[position].toString())
    }

    fun removeItem(item: PictureClass) {
        val index = items.indexOf(item)
        if (index != -1) {
            items.remove(item)
            notifyItemRemoved(index)
            item.file?.let { file ->
                if (file.exists()) {
                    if (file.delete()) {
                        Log.d("removeItem", "Archivo eliminado: ${file.path}")
                    } else {
                        Log.d("removeItem", "Archivo no se pudo eliminar: ${file.path}")
                    }
                } else {
                    Log.d("removeItem", "Archivo no existe: ${file.path}")
                }
            }
        }
    }

    interface SelectionDialogActions {
        fun onSelected(item: PictureClass)
    }

    // Retorna la cantidad de valores
    override fun getItemCount() = items.size
}
