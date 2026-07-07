package com.films.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.films.data.local.SettingsManager
import com.films.ui.components.SetLanguage
import com.films.ui.components.SetTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    val themeState: StateFlow<SetTheme> = settingsManager.themeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetTheme.SYSTEM
    )

    val languageState: StateFlow<SetLanguage> = settingsManager.languageFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetLanguage.ENGLISH
    )


    fun changeTheme(newTheme: SetTheme) {
        viewModelScope.launch {
            settingsManager.saveTheme(newTheme)
        }
    }

    fun changeLanguage(newLanguage: SetLanguage) {
        viewModelScope.launch {
            settingsManager.saveLanguage(newLanguage)
        }
    }
}