package todoApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R

class TaskAdapter(
    var tasks: List<Task>, // Lista de tareas
    private val onTaskClick: (Int) -> Unit // Función que se ejecutará al hacer click en una tarea
) : RecyclerView.Adapter<TaskViewHolder>() {

    // Se crea la vista de la tarea
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo_task, parent, false)
        return TaskViewHolder(view)
    }

    // Se renderiza la tarea
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position]) // Se renderiza la tarea
        holder.itemView.setOnClickListener { onTaskClick(position) } // Se agrega el evento click
    }

    // Se obtiene la cantidad de tareas
    override fun getItemCount(): Int {
        return tasks.size
    }
}