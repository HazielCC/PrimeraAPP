package androidearly.primeraApp.imcCaltulator

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidearly.primeraApp.imcCaltulator.ImcCaltulatorActivity.Companion.IMC_KEY
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coco.primeraapp.R
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {
    // Función para inicializar los componentes de la actividad
    private lateinit var tvEstatusIMC: TextView
    private lateinit var tvTotalIMC: TextView
    private lateinit var tvResultado: TextView
    private lateinit var btVolver: MaterialButton


    // Variables de código
    private val tag = "ResultActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val result = intent.extras?.getDouble(IMC_KEY) ?: 0.0
        initComponents()
        initUI(result)
        setListeners()
    }

    private fun setListeners() {
        btVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initComponents() {
        // Se declaran componentes del Activity
        tvResultado = findViewById(R.id.tvResultado)
        tvEstatusIMC = findViewById(R.id.tvEstatusIMC)
        tvTotalIMC = findViewById(R.id.tvTotalIMC)
        btVolver = findViewById(R.id.btVolver)
    }

    private fun initUI(result: Double) {
        tvTotalIMC.text = result.toString()
        when (result) {
            in 0.00..18.50 -> { // Peso bajo
                tvResultado.text = getString(R.string.titulo_bajo_peso)
                tvResultado.setTextColor(ContextCompat.getColor(this, R.color.peso_insuficiente))
                tvEstatusIMC.text = getString(R.string.estatus_bajo_peso)
            }

            in 18.50..24.99 -> { // Peso Normal
                tvResultado.text = getString(R.string.titulo_nomal_peso)
                tvResultado.setTextColor(ContextCompat.getColor(this, R.color.peso_saludable))
                tvEstatusIMC.text = getString(R.string.estatus_nomal_peso)
            }

            in 25.00..29.99 -> { // Sobre Peso
                tvResultado.text = getString(R.string.titulo_sobrepeso)
                tvResultado.setTextColor(ContextCompat.getColor(this, R.color.exceso_peso))
                tvEstatusIMC.text = getString(R.string.estatus_sobrepeso)
            }

            in 30.00..99.99 -> { // Obesidad
                tvResultado.text = getString(R.string.titulo_obesidad)
                tvResultado.setTextColor(ContextCompat.getColor(this, R.color.estado_obesidad))
                tvEstatusIMC.text = getString(R.string.estatus_obesidad)
            }

            else -> {
                tvResultado.text = getString(R.string.error)
                tvResultado.setTextColor(ContextCompat.getColor(this, R.color.estado_obesidad))
                tvEstatusIMC.text = getString(R.string.error)
                tvTotalIMC.text = getString(R.string.error)
                Log.e(tag, "No se Proporciono el IMC")
            }

        }
    }

}