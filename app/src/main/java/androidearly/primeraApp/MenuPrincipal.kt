package androidearly.primeraApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidearly.heroApp.SuperHeroListActivity
import androidearly.primeraApp.fotosApp.FotosActivity
import androidearly.primeraApp.imcCaltulator.ImcCaltulatorActivity
import androidearly.primeraApp.todoApp.TodoActivity
import androidearly.settings.SettingsActivity
import androidx.appcompat.app.AppCompatActivity
import com.coco.primeraapp.databinding.ActivitySegundaVentanaBinding

class MenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivitySegundaVentanaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaVentanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
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

    // Función para inicializar el estado de la segunda ventana
    private fun initUI() {
        imprimir() // Función para imprimir
    }

    // Función para mandar a imprimir
    private fun imprimir() {
        binding.btnImprimir.setOnClickListener {
            val intent =
                this.packageManager.getLaunchIntentForPackage("mx.com.casaley.printutility")
            if (intent != null) {
                Log.d("TAG", "Se encontró la aplicación")
                intent.putExtra(
                    "label", "{C|}\n" +
                            "{D0320,0780,0250|}\n" +
                            "{XB00;0730,0108,2,1,03,03,08,08,03,2,0048,+0000000000,0,00=00068736|}\n" +
                            "{PV00;0745,0200,0033,0042,B,22,B=POLLO ENTERO FRESCO BACHOCO|}\n" +
                            "{PV01;0745,0155,0038,0038,B,22,B=1 KG         |}\n" +
                            "{PV02;0760,0040,0020,0020,B,22,B=C.L.00068736|}\n" +
                            "{PV03;0595,0040,0020,0020,B,22,B=C.B.0204249000000|}\n" +
                            "{PV04;0585,0009,0020,0020,B,22,B=PD|}\n" +
                            "{PV05;0760,0009,0020,0020,B,22,B=SF003|}\n" +
                            "{PV06;0667,0009,0020,0020,B,22,B=Cat019|}\n" +
                            "{PV07;0540,0009,0020,0020,B,22,B=Imp. 9/jul/24|}\n" +
                            "{PV08;0180,0023,0081,0211,B,22,B=57|}\n" +
                            "{PV09;0070,0114,0040,0095,B,22,B=90|}\n" +
                            "{PV10;0230,0070,0076,0123,B,22,B=\$|}\n" +
                            "{PV11;0500,0136,0020,0020,B,22,B=   |}\n" +
                            "{XS;I,0012,0002C1011|}"
                )
                intent.putExtra("mac", "F4:60:77:4C:B7:C3")
                this.startActivity(intent)
            } else {
                Log.d("TAG", "No se encontró la aplicación")
            }
        }
    }

}
