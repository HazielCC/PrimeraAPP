package androidearly.primeraApp.imcCaltulator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.coco.primeraapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView
import java.text.DecimalFormat

class ImcCaltulatorActivity : AppCompatActivity() {
    // Función para inicializar los componentes de la actividad
    private lateinit var maleCard: MaterialCardView
    private lateinit var famaleCard: MaterialCardView
    private lateinit var tvAltura: MaterialTextView
    private lateinit var rsAltura: RangeSlider
    private lateinit var ibMenos: ImageButton
    private lateinit var ibMas: ImageButton
    private lateinit var tvPeso: TextView
    private lateinit var ibMenosEdad: ImageButton
    private lateinit var ibMasEdad: ImageButton
    private lateinit var tvEdad: TextView
    private lateinit var btCalcular: MaterialButton

    // Variables de código
    private var peso = 45
    private var edad = 23
    private var altura = 120
    private var isMaleSelected = true
    private var isFamaleSelected = false
    private var result = 0.0
    private val tag = "ImcCaltulatorActivity"

    // Variables Globales
    companion object {
        const val IMC_KEY = "IMC_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_caltulator)
        initComponets()
        initUI()
        setListeners()
    }


    // Inicialización de Variables
    private fun initComponets() {
        // Se declaran componentes del Activity
        famaleCard = findViewById(R.id.FamaleView)
        maleCard = findViewById(R.id.MaleView)
        tvAltura = findViewById(R.id.tvAltura)
        rsAltura = findViewById(R.id.rsAltura)
        ibMenos = findViewById(R.id.ibMenos)
        ibMas = findViewById(R.id.ibMas)
        tvPeso = findViewById(R.id.tvPeso)
        tvEdad = findViewById(R.id.tvEdad)
        ibMenosEdad = findViewById(R.id.ibMenosEdad)
        ibMasEdad = findViewById(R.id.ibMasEdad)
        btCalcular = findViewById(R.id.btCalcular)
    }

    private fun initUI() {
        setGernderColor()
        actualizarPeso()
    }

    private fun calcularIMC(): Double {
        Log.d(tag, "se Calcula el IMC")
        result = peso / (altura.toDouble() / 100 * altura.toDouble() / 100)
        Log.d(tag, "El Resultado es $result")
        return result

    }

    // Inicialización de Variables
    @SuppressLint("SetTextI18n")
    private fun setListeners() {
        // Botón Mujer que hace referencia a la tarjeta de mujer
        famaleCard.setOnClickListener {
            Log.d(tag, "Famale card clicked")
            changeColor()
            setGernderColor()
        }

        // Botón Hombre que hace referencia a la tarjeta de Hombre
        maleCard.setOnClickListener {
            Log.d(tag, "Male card clicked")
            changeColor()
            setGernderColor()
        }

        // Control deslizante de rango de Altura
        rsAltura.addOnChangeListener { _, fl, _ ->
            val df = DecimalFormat("#.##")
            altura = df.format(fl).toInt()
            tvAltura.text = "$altura cm"
        }

        // Apartado de Peso
        //tvPeso.text = peso.toString()
        ibMenos.setOnClickListener {
            peso -= 1
            actualizarPeso()
        }

        ibMas.setOnClickListener {
            peso += 1
            actualizarPeso()
        }

        // Apartado de Edad
        ibMenosEdad.setOnClickListener {
            edad -= 1
            actualizarEdad()
        }

        ibMasEdad.setOnClickListener {
            edad += 1
            actualizarEdad()
        }

        btCalcular.setOnClickListener {
            calcularIMC()
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        // Inicia la nueva actividad
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun actualizarEdad() {
        tvEdad.text = "$edad años"
    }

    @SuppressLint("SetTextI18n")
    private fun actualizarPeso() {
        tvPeso.text = "$peso kg"
    }

    private fun changeColor() {
        isMaleSelected = !isMaleSelected
        isFamaleSelected = !isFamaleSelected
    }


    private fun setGernderColor() {
        maleCard.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        famaleCard.setCardBackgroundColor(getBackgroundColor(isFamaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        val colorSelected = if (isSelectedComponent) {
            R.color.btnClick
        } else {
            R.color.btn
        }
        return ContextCompat.getColor(this, colorSelected)
    }
}

