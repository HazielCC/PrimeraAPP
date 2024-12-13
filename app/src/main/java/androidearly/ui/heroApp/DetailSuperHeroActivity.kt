package androidearly.ui.heroApp

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidearly.ui.heroApp.data.SuperHeroDetailResponse
import androidearly.utilities.notifications.showCustomToastNotification
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivityDetailSuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {
    // Obtengo Intents
    private lateinit var idSuperHero: String
    private val tag = "DetailSuperHeroActivity"
    private lateinit var retrofit: Retrofit
    private lateinit var binding: ActivityDetailSuperHeroBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root) // Corrected to use binding.root instead of R.layout.activity_detail_super_hero
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initUI()
    }

    private fun initComponents() {
        idSuperHero = intent.getStringExtra(EXTRA_NAME).orEmpty()
        Log.d(tag, idSuperHero)
        retrofit = getRetrofit()
    }

    private fun initUI() {
        searchByID(idSuperHero)
    }

    private fun searchByID(idSuperHero: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = retrofit.create(ApiService::class.java).getHeroesID(idSuperHero)
                Log.d(tag, service.toString())
                runOnUiThread {
                    if (service.body() != null && service.isSuccessful) {
                        Log.d(tag, "succes " + service.body().toString())
                        setInfoToLayout(service.body()!!)
                    } else {
                        Log.e(tag, service.message())
                        showCustomToastNotification("Error al cargar la información", 3)
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }

        }
    }

    private fun setInfoToLayout(body: SuperHeroDetailResponse) {
        if (body.image?.url != null) {
            Glide.with(binding.ivIDHero)
                .load(body.image.url)
                .placeholder(R.drawable.ic_launcher_foreground) // Reemplaza con tu recurso de imagen placeholder
                .error(R.drawable.ic_launcher_foreground) // Reemplaza con tu recurso de imagen de error
                .into(binding.ivIDHero)
        } else {
            // Si la URL es nula, carga una imagen alternativa
            Glide.with(binding.ivIDHero)
                .load(R.drawable.ic_launcher_foreground) // Reemplaza con tu recurso de imagen predeterminada
                .into(binding.ivIDHero)
        }

        binding.tvSuperHeroeName.text = body.name ?: "Name not found"
        binding.tvSuperHeroePublisher.text = body.biography?.publisher ?: "Publisher not found"
        binding.tvRealName.text = body.biography?.fullName ?: "Real name not found"
        binding.tvFirstAppearance.text =
            body.biography?.firstAppearance ?: "First appearance not found"
        setStatsOvView(body)
    }

    private fun setStatsOvView(body: SuperHeroDetailResponse) {
        body.powerstats?.combat?.let { updateHeight(binding.vCombat, it) }
        body.powerstats?.durability?.let { updateHeight(binding.vDurability, it) }
        body.powerstats?.intelligence?.let { updateHeight(binding.vIntelligence, it) }
        body.powerstats?.power?.let { updateHeight(binding.vPower, it) }
        body.powerstats?.speed?.let { updateHeight(binding.vSpeed, it) }
        body.powerstats?.strength?.let { updateHeight(binding.vStrength, it) }
    }

    private fun updateHeight(view: View, parametros: String?) {
        val params = view.layoutParams
        val height = parametros?.let {
            try {
                pxToDp(it.toFloat())
            } catch (e: NumberFormatException) {
                pxToDp(0f)
            }
        } ?: pxToDp(0f) // Valor predeterminado si parametro es null
        params.height = height
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px,
            resources.displayMetrics
        ).roundToInt()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
