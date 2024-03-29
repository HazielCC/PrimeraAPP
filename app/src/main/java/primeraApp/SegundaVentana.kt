package primeraApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.primeraapp.R
import imcCaltulator.ImcCaltulatorActivity

class SegundaVentana : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_ventana)
        initState()
        primerVentana()
        segundaVentana()
    }

    // Función para inicializar el estado de la segunda ventana
    private fun initState() {
    }

    // Función para el botón de la primera ventana
    private fun primerVentana() {
        val btnPrimerVentana = findViewById<Button>(R.id.btnPrimerVentana)
        btnPrimerVentana.setOnClickListener {
            val intent = Intent(this, PrimerVentana::class.java)
            startActivity(intent)
        }
    }

    // Función para el botón de la segunda ventana
    private fun segundaVentana() {
        val btnIMCVentana = findViewById<Button>(R.id.btnIMCVentana)
        btnIMCVentana.setOnClickListener {
            val intent = Intent(this, ImcCaltulatorActivity::class.java)
            startActivity(intent)
        }
    }
}