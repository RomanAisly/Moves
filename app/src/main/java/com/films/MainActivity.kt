package com.films

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.films.ui.navigation.RootNavGraph
import com.films.ui.screens.settings.SettingsViewModel
import com.films.ui.theme.FilmsTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val currentTheme by settingsViewModel.themeState.collectAsStateWithLifecycle()

            FilmsTheme(appTheme = currentTheme, onThemeChange = { newTheme ->
                settingsViewModel.changeTheme(newTheme)
            }) {
                RootNavGraph(rootNavHost = navController)
            }
        }
    }
}