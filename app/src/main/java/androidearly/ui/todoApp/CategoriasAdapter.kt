package androidearly.primeraApp.todoApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R

class CategoriasAdapter(
    private val categorias: List<TaskCategory>,
    private val onCategorySelected: (Int) -> Unit
) : RecyclerView.Adapter<CategoriasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return CategoriasViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriasViewHolder, position: Int) {
        holder.render(categorias[position], onCategorySelected)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }
}