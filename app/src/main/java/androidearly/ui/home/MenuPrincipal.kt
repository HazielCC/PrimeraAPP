package androidearly.ui.home

import android.content.Intent
import android.os.Bundle
import androidearly.primeraApp.fotosApp.FotosActivity
import androidearly.ui.imcCaltulatorApp.ImcCaltulatorActivity
import androidearly.primeraApp.todoApp.TodoActivity
import androidearly.ui.heroApp.SuperHeroListActivity
import androidearly.ui.settings.SettingsActivity
import androidx.appcompat.app.AppCompatActivity
import com.coco.primeraapp.databinding.ActivitySegundaVentanaBinding

class MenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivitySegundaVentanaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaVentanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setListeners()
    }

    private fun setListeners() {
        // Función para el botón de la primera ventana
        binding.btnPrimerApp.setOnClickListener {
            val intent = Intent(this, PrimerVentana::class.java)
            startActivity(intent)
        }

        // Función para el botón de la segunda ventana Indice de Masa Corporal
        binding.btnIMCApp.setOnClickListener {
            val intent = Intent(this, ImcCaltulatorActivity::class.java)
            startActivity(intent)
        }

        // Función para el botón de la tercera ventana de la aplicación de tareas
        binding.btnToDoApp.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }

        // Función para el botón de la cuarta ventana de Fotografias
        binding.btnFotosApp.setOnClickListener {
            val intent = Intent(this, FotosActivity::class.java)
            startActivity(intent)
        }

        // Función para el botón de la quinta ventana de SuperHeroes
        binding.btnHeroApp.setOnClickListener {
            val intent = Intent(this, SuperHeroListActivity::class.java)
            startActivity(intent)
        }

        // Función para el botón para los ajustes de la aplicación
        binding.btnAjustesApp.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
