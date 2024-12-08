package androidearly.ui.heroApp

import android.content.Intent
import android.os.Bundle
import androidearly.ui.heroApp.DetailSuperHeroActivity.Companion.EXTRA_NAME
import androidearly.ui.heroApp.adapter.SuperHeroAdapter
import androidearly.utilities.dialogs.LoadingDialog.Companion.dismissLoadingDialog
import androidearly.utilities.dialogs.LoadingDialog.Companion.showLoadingDialog
import androidearly.utilities.notifications.showCustomToastNotification
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivitySuperHeroListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperHeroListBinding
    private lateinit var retrofit: Retrofit
    private val tag = "SuperHeroListActivity"
    private lateinit var adapter: SuperHeroAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuperHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponts()
        initUI()
    }

    private fun initComponts() {
        retrofit = getRetrofit()
        adapter = SuperHeroAdapter { navigateToDetail(it) }
    }

    private fun initUI() {
        // Se prepara el searchView
        binding.svSuperHeroList.queryHint = "Buscar Superhéroe"
        binding.svSuperHeroList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Se ejecuta cuando se presiona el botón de búsqueda
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty()) // Se ejecuta la búsqueda
                return false
            }

            // Se ejecuta cada vez que se escribe en el SearchView
            override fun onQueryTextChange(newText: String?) = false
        })

        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {
        //binding.pbSuperHeroList.isVisible = true
        showLoadingDialog(this@SuperHeroListActivity)
        CoroutineScope(Dispatchers.IO).launch {
            val service = retrofit.create(ApiService::class.java).getHeroes(query)
            val response = service.body()
            runOnUiThread {
                dismissLoadingDialog(this@SuperHeroListActivity)
                //binding.pbSuperHeroList.isVisible = false
                if (response!!.superHeroList.isNotEmpty() && service.isSuccessful) {
                    adapter.updateList(response.superHeroList)
                } else {
                    showCustomToastNotification("No se encontró informacion", 3)
                }
            }
        }
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(EXTRA_NAME, id)
        startActivity(intent)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
