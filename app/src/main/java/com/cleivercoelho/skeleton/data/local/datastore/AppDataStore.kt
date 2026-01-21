package com.cleivercoelho.skeleton.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cleivercoelho.skeleton.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DATABASE_NAME
)

@Singleton
class AppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    }

    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.AUTH_TOKEN]
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
    }

    val lastSyncTime: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LAST_SYNC_TIME] ?: 0L
    }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun updateLastSyncTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_SYNC_TIME] = time
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}