package primeraApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.coco.primeraapp.R
import fotosApp.FotosActivity
import imcCaltulator.ImcCaltulatorActivity
import todoApp.TodoActivity

class MenuPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_ventana)
        initUI()
    }

    // Función para inicializar el estado de la segunda ventana
    private fun initUI() {
        primerVentana()
        imcApp()
        todoApp()
        fotosApp()
    }

    // Función para el botón de la primera ventana
    private fun primerVentana() {
        val btnPrimerVentana = findViewById<Button>(R.id.btnPrimerApp)
        btnPrimerVentana.setOnClickListener {
            val intent = Intent(this, PrimerVentana::class.java)
            startActivity(intent)
        }
    }

    // Función para el botón de la segunda ventana
    private fun imcApp() {
        val btnIMCVentana = findViewById<Button>(R.id.btnIMCApp)
        btnIMCVentana.setOnClickListener {
            val intent = Intent(this, ImcCaltulatorActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para el botón de la Tercer Ventana App de Tareas
    private fun todoApp() {
        val btnToDoApp = findViewById<Button>(R.id.btnToDoApp)
        btnToDoApp.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para el botón para la cuarta ventana de Fotografias
    private fun fotosApp() {
        val btnToDoApp = findViewById<Button>(R.id.btnFotosApp)
        btnToDoApp.setOnClickListener {
            val intent = Intent(this, FotosActivity::class.java)
            startActivity(intent)
        }
    }
}
