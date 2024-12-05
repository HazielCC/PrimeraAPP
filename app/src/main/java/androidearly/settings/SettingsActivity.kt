package androidearly.settings

import android.os.Bundle
import android.util.Log
import androidearly.core.Settings
import androidearly.core.dataStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val tag = "Settings"

    // Variable para evitar que se ejecute el evento de cambio de estado al iniciar la actividad
    private var firstTime = true

    // Constantes
    companion object SettingsConstans {
        const val KEY_VOLUMEN = "key_volumen"
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_VIBRATION = "key_vibration"
        const val KEY_BLUETOOTH_MODE = "key_bluetooth_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CoroutineScope(Dispatchers.IO).launch {
            getSetting().filter { firstTime }.collect {
                runOnUiThread {
                    binding.rsVolumen.setValues(it.volumen.toFloat())
                    binding.switchModoOscuro.isChecked = it.darkMode
                    binding.switchVibration.isChecked = it.vibration
                    binding.switchBluetooth.isChecked = it.bluetoothMode
                    firstTime = !firstTime
                }
            }
        }
        initUI()
    }

    private fun initUI() {
        // Volumen
        binding.rsVolumen.addOnChangeListener { _, value, _ ->
            Log.d(tag, "Volumen: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }

        // Bluetooth
        binding.switchBluetooth.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tag, "Bluetooth: $isChecked")
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(isChecked, KEY_BLUETOOTH_MODE)
            }
        }

        // Modo Oscuro
        binding.switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tag, "Dark Mode: $isChecked")
            if (isChecked) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }
        }

        // Vibración
        binding.switchVibration.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tag, "Vibration: $isChecked")
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(isChecked, KEY_VIBRATION)
            }
        }
    }

    private suspend fun saveVolume(volumen: Int) {
        dataStore.edit { preference ->
            preference[intPreferencesKey(KEY_VOLUMEN)] = volumen
        }
    }

    private suspend fun saveOptions(option: Boolean, key: String) {
        dataStore.edit { preference ->
            preference[booleanPreferencesKey(key)] = option
        }
    }

    private fun getSetting(): Flow<Settings> {
        return dataStore.data.map { preference ->
            Settings(
                preference[intPreferencesKey(KEY_VOLUMEN)] ?: 0,
                preference[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                preference[booleanPreferencesKey(KEY_VIBRATION)] ?: false,
                preference[booleanPreferencesKey(KEY_BLUETOOTH_MODE)] ?: false
            )
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}