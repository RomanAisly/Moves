package com.films.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.films.ui.components.SetLanguage
import com.films.ui.components.SetTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val dataStore: DataStore<Preferences>) {

    private inline fun <reified T : Enum<T>> getEnumFlow(
        key: Preferences.Key<String>,
        defaultValue: T
    ): Flow<T> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val name = prefs[key] ?: return@map defaultValue
            runCatching { enumValueOf<T>(name) }.getOrDefault(defaultValue)
        }

    val themeFlow: Flow<SetTheme> = getEnumFlow(THEME_KEY, SetTheme.SYSTEM)
    val languageFlow: Flow<SetLanguage> = getEnumFlow(LANGUAGE_KEY, SetLanguage.ENGLISH)

    suspend fun saveTheme(theme: SetTheme) {
        dataStore.edit { it[THEME_KEY] = theme.name }
    }

    suspend fun saveLanguage(language: SetLanguage) {
        dataStore.edit { it[LANGUAGE_KEY] = language.name }
    }

    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }
}
