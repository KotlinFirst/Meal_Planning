package com.example.mealplanning.data.local.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fastVar")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    companion object {
        private val BUTTON_STATE_KEY = booleanPreferencesKey("save_button_state")
    }

    val buttonStateFlow: Flow<Boolean> = context.dataStore.data.map {
        it[BUTTON_STATE_KEY] ?: false
    }
    suspend fun setButtonState(isSelected: Boolean){
        Log.d("PreferencesManager","$isSelected")
        context.dataStore.edit { it[BUTTON_STATE_KEY] = isSelected }
    }

}