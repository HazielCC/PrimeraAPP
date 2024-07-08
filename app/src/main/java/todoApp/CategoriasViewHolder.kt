package todoApp

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R

class CategoriasViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val cvCategory: CardView = view.findViewById(R.id.cvCategory)
    private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
    private val vDivider: View = view.findViewById(R.id.vDivider)
    fun render(taskCategory: TaskCategory, onCategorySelected: (Int) -> Unit) {

        /* Se renderiza la categoría*/
        val colorCategory = if (taskCategory.isSelected) {
            R.color.amistades
        } else {
            R.color.azul_verde
        }
        cvCategory.setCardBackgroundColor(
            ContextCompat.getColor(itemView.context, colorCategory)
        )

        itemView.setOnClickListener { onCategorySelected(adapterPosition) }

        // Se establece el nombre de la categoría
        val name = when (taskCategory) {
            TaskCategory.Business -> "Negocios"
            TaskCategory.Other -> "Otro"
            TaskCategory.Personal -> "Personal"
        }
        tvCategoryName.text = name

        // Se establece el color de la categoría
        val color = when (taskCategory) {
            TaskCategory.Business -> R.color.trabajo
            TaskCategory.Other -> R.color.ocio
            TaskCategory.Personal -> R.color.pasatiempo
        }
        vDivider.setBackgroundColor(
            ContextCompat.getColor(vDivider.context, color)
        )
    }
}