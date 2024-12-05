package androidearly.ui.settings.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class Settings(
    val volumen: Int,
    val darkMode: Boolean,
    val vibration: Boolean,
    val bluetoothMode: Boolean
)
