package com.universe.audioflare.data.dataStore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.Player
import com.universe.audioflare.common.SELECTED_LANGUAGE
import com.universe.audioflare.common.SPONSOR_BLOCK
import com.universe.audioflare.common.SUPPORTED_LANGUAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.universe.audioflare.common.QUALITY as COMMON_QUALITY

class DataStoreManager @Inject constructor(private val settingsDataStore: DataStore<Preferences>) {

    // Location
    val location: Flow<String> =
        settingsDataStore.data.map { preferences ->
            preferences[LOCATION] ?: "IN"
        }

    suspend fun setLocation(location: String) {
        withContext(Dispatchers.IO) {
            settingsDataStore.edit { settings ->
                settings[LOCATION] = location
            }
        }
    }

    // Quality
    val quality: Flow<String> =
        settingsDataStore.data.map { preferences ->
            preferences[QUALITY] ?: COMMON_QUALITY.items[0].toString()
        }

    suspend fun setQuality(quality: String) {
        withContext(Dispatchers.IO) {
            settingsDataStore.edit { settings ->
                settings[QUALITY] = quality
            }
        }
    }

    // Language
    val language: Flow<String> =
        settingsDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(SELECTED_LANGUAGE)] ?: SUPPORTED_LANGUAGE.codes.first()
        }

    // Generic get/set for String values
    fun getString(key: String): Flow<String?> {
        return settingsDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    suspend fun putString(key: String, value: String) {
        settingsDataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    // Logged In
    val loggedIn: Flow<String> =
        settingsDataStore.data.map { preferences ->
            preferences[LOGGED_IN] ?: FALSE
        }

    // Cookie
    val cookie: Flow<String> =
        settingsDataStore.data.map { preferences ->
            preferences[COOKIE] ?: ""
        }

    suspend fun setCookie(cookie: String) {
        withContext(Dispatchers.IO) {
            settingsDataStore.edit { settings ->
                settings[COOKIE] = cookie
            }
        }
    }

    // Boolean preferences with TRUE/FALSE
    suspend fun setLoggedIn(logged: Boolean) {
        withContext(Dispatchers.IO) {
            settingsDataStore.edit { settings ->
                settings[LOGGED_IN] = if (logged) TRUE else FALSE
            }
        }
    }

    // Additional preferences...

    companion object Settings {
        // Preferences keys
        val COOKIE = stringPreferencesKey("cookie")
        val LOGGED_IN = stringPreferencesKey("logged_in")
        val LOCATION = stringPreferencesKey("location")
        val QUALITY = stringPreferencesKey("quality")
        // Define other keys as needed
        const val TRUE = "TRUE"
        const val FALSE = "FALSE"
    }
}
