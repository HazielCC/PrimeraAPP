package primeraApp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.primeraapp.databinding.ActivityPrimerVentanaBinding

class PrimerVentana : AppCompatActivity() {
    // Se declara blinding para los elementos de la vista
    private lateinit var binding: ActivityPrimerVentanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrimerVentanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
    }


    private fun listeners() {

        /*initState()*/
        val editText = binding.appCompatEditText // Se declara el binding para el EditText
        val spinner = binding.spinner // Se declara el binding para el Spinner
        val textViewLista = binding.textViewLista // Se declara el binding para el TextView

        // Crear un adaptador para el Spinner
        val listItems = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        spinner.adapter = adapter

        binding.btnADD.setOnClickListener {
            if (binding.appCompatEditText.text.toString().isNotEmpty()) {
                // Añadir el texto ingresado a la lista y notificar al adaptador
                listItems.add(editText.text.toString())
                adapter.notifyDataSetChanged()
                Log.i("Log", "botón: ${editText.text.toString()}")
            } else {
                Log.i("Log", "El campo de Texto Está vacio")
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
                Log.i("Log", "No se seleccionó nada")
            }
        }
    }


}
