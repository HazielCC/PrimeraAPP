package androidearly.primeraApp.imcCaltulator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityImcCaltulatorBinding
import java.text.DecimalFormat

class ImcCaltulatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImcCaltulatorBinding

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
        binding = ActivityImcCaltulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponets()
        initUI()
        setListeners()
    }


    // Inicialización de Variables
    private fun initComponets() {

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
        binding.cvFamale.setOnClickListener {
            Log.d(tag, "Famale card clicked")
            changeColor()
            setGernderColor()
        }

        // Botón Hombre que hace referencia a la tarjeta de Hombre
        binding.cvMale.setOnClickListener {
            Log.d(tag, "Male card clicked")
            changeColor()
            setGernderColor()
        }

        // Control deslizante de rango de Altura
        binding.rsAltura.addOnChangeListener { _, fl, _ ->
            val df = DecimalFormat("#.##")
            altura = df.format(fl).toInt()
            binding.tvAltura.text = "$altura cm"
        }

        // Apartado de Peso
        //binding.tvPeso.text = peso.toString()
        binding.ibMenos.setOnClickListener {
            peso -= 1
            actualizarPeso()
        }

        binding.ibMas.setOnClickListener {
            peso += 1
            actualizarPeso()
        }

        // Apartado de Edad
        binding.ibMenosEdad.setOnClickListener {
            edad -= 1
            actualizarEdad()
        }

        binding.ibMasEdad.setOnClickListener {
            edad += 1
            actualizarEdad()
        }

        binding.btCalcular.setOnClickListener {
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
        binding.tvEdad.text = "$edad años"
    }

    @SuppressLint("SetTextI18n")
    private fun actualizarPeso() {
        binding.tvPeso.text = "$peso kg"
    }

    private fun changeColor() {
        isMaleSelected = !isMaleSelected
        isFamaleSelected = !isFamaleSelected
    }


    private fun setGernderColor() {
        binding.cvMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        binding.cvFamale.setCardBackgroundColor(getBackgroundColor(isFamaleSelected))
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
