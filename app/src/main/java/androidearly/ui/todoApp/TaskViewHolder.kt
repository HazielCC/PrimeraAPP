package androidearly.primeraApp.todoApp

import android.content.res.ColorStateList
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R

class TaskViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val tvTodoTittle: TextView = view.findViewById(R.id.tvTodoTittle)
    private val cbTodoCheck: CheckBox = view.findViewById(R.id.cb_todo_check)

    fun render(task: Task) {
        // Se renderiza la tarea
        cbTodoCheck.isChecked = task.isSelected // Se establece el estado del checkbox

        // Se establece el color del checkbox según la categoría
        val color = when (task.category) {
            TaskCategory.Business -> R.color.trabajo
            TaskCategory.Other -> R.color.ocio
            TaskCategory.Personal -> R.color.pasatiempo
        }
        // Se establece el color del checkbox
        cbTodoCheck.buttonTintList =
            ColorStateList.valueOf(itemView.resources.getColor(color, itemView.context.theme))

        tvTodoTittle.text = task.name // Se establece el nombre de la tarea
        // Se establece el texto en negrita si la tarea está seleccionada
        tvTodoTittle.isActivated = task.isSelected
    }
}