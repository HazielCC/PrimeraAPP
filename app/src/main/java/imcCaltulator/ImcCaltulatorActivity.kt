package imcCaltulator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.primeraapp.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView

class ImcCaltulatorActivity : AppCompatActivity() {
    // Función para inicializar los componentes de la actividad
    private lateinit var maleCard: MaterialCardView
    private lateinit var famaleCard: MaterialCardView
    private lateinit var tvAltura: MaterialTextView
    private lateinit var rsAltura: RangeSlider

    // Variables de código
    private var isMaleSelected = true
    private var isFamaleSelected = false
    private val tag = "ImcCaltulatorActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_caltulator)
        initComponets()
        setListeners()
        initUI()
    }


    // Inicialización de Variables
    private fun initComponets() {
        // Se declaran componentes del Activity
        famaleCard = findViewById(R.id.FamaleView)
        maleCard = findViewById(R.id.MaleView)
        tvAltura = findViewById(R.id.tvAltura)
        rsAltura = findViewById(R.id.rsAltura)
    }

    // Inicialización de Variables
    private fun setListeners() {
        famaleCard.setOnClickListener {
            Log.d(tag, "Famale card clicked")
            changeColor()
            setGernderColor()
        }
        maleCard.setOnClickListener {
            Log.d(tag, "Male card clicked")
            changeColor()
            setGernderColor()
        }
    }

    private fun changeColor() {
        isMaleSelected = !isMaleSelected
        isFamaleSelected = !isFamaleSelected
    }

    private fun initUI() {
        setGernderColor()
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

