package com.films.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.films.data.local.SettingsManager
import com.films.ui.components.AppTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    val themeState: StateFlow<AppTheme> = settingsManager.themeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppTheme.SYSTEM
    )

    fun changeTheme(newTheme: AppTheme) {
        viewModelScope.launch {
            settingsManager.saveTheme(newTheme)
        }
    }

}