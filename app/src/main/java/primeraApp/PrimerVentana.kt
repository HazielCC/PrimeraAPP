package primeraApp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.coco.primeraapp.databinding.ActivityPrimerVentanaBinding

class PrimerVentana : AppCompatActivity() {
    // Se declara blinding para los elementos de la vista
    private lateinit var binding: ActivityPrimerVentanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrimerVentanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }


    private fun setupListeners() {
        val editText = binding.appCompatEditText // Se declara el binding para el EditText
        val spinner = binding.spinner // Se declara el binding para el Spinner
        val textViewLista = binding.textViewLista // Se declara el binding para el TextView

        // Crear un adaptador para el Spinner
        val listItems = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        spinner.adapter = adapter

        binding.btnADD.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isNotEmpty()) {
                listItems.add(inputText)
                adapter.notifyDataSetChanged()
                Log.i("Log", "Botón hecho clic: $inputText")
            } else {
                Log.i("Log", "El campo de texto está vacío")
            }
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                textViewLista.text = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("Log", "Nada seleccionado")
            }
        }
    }
}
