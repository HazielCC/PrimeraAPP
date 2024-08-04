package androidearly.primeraApp.todoApp

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidearly.primeraApp.todoApp.TaskCategory.Business
import androidearly.primeraApp.todoApp.TaskCategory.Other
import androidearly.primeraApp.todoApp.TaskCategory.Personal
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {
    //#Region Variables
    private lateinit var rvCategorias: RecyclerView
    private lateinit var rvTasks: RecyclerView
    private lateinit var categoriasAdapter: CategoriasAdapter
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var fabAddTask: FloatingActionButton

    private val categorias = listOf(
        Other,
        Personal,
        Business
    )
    private val tasks = mutableListOf(
        Task("Tarea 1", Other),
        Task("Tarea 2", Personal),
        Task("Tarea 3", Business),
        Task("Tarea 4", Other),
    )
    //#EndRegion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponent()
        initUI()
        initListeners()
    }

    //#Region Functions
    // Se inicializan los componentes
    private fun initComponent() {
        // Se inicializan los componentes
        rvCategorias = findViewById(R.id.rvCategorias)
        rvTasks = findViewById(R.id.rvTasks)
        // Se inicializa el adaptador de tareas
        categoriasAdapter = CategoriasAdapter(categorias) { onCategorySelected(it) }
        taskAdapter = TaskAdapter(tasks) { onTaskSelected(it) }
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    // Se inicializa la interfaz de usuario
    private fun initUI() {
        rvCategorias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategorias.adapter = categoriasAdapter
        rvTasks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTasks.adapter = taskAdapter
    }

    // Se inicializan los listeners
    private fun initListeners() {
        fabAddTask.setOnClickListener {
            showDialog()
        }
    }

    // Se ejecuta al seleccionar una tarea
    private fun onTaskSelected(position: Int) {
        // Se cambia el estado de la tarea
        tasks[position].isSelected = !tasks[position].isSelected
        updateTask()
    }

    private fun onCategorySelected(position: Int) {
        // Se filtran las tareas por categoría
        categorias[position].isSelected = !categorias[position].isSelected
        updateCategory()
        updateTask()
    }
    //#EndRegion

    // #Region Funciones de la interfaz
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnSave: Button = dialog.findViewById(R.id.btnSave)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rgCategory: RadioGroup = dialog.findViewById(R.id.rgCategory)  // RadioGroup

        btnSave.setOnClickListener {
            if (etTask.text.isNotEmpty()) {
                val task = Task(
                    etTask.text.toString(), when (rgCategory.checkedRadioButtonId) {
                        R.id.rbPersonal -> Personal
                        R.id.rbBusiness -> Business
                        else -> Other
                    }
                )
                tasks.add(task)
                updateTask()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    // Se actualiza la lista de tareas
    private fun updateTask() {
        val selectedCategory = categorias.filter { it.isSelected }
        val selectedTasks = tasks.filter { selectedCategory.contains(it.category) }
        taskAdapter.tasks = selectedTasks
        taskAdapter.notifyDataSetChanged()
    }

    private fun updateCategory() {
        categoriasAdapter.notifyDataSetChanged()
    }
}
