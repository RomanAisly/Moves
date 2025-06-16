package com.moves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.moves.domain.navigation.Screens
import com.moves.ui.components.SettingMenuItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateTo: (Screens) -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SettingMenuItem(
            modifier = modifier,
            onClick = {navigateTo(Screens.SetLanguageScreen)},
            setTitle = "Set Language"
        )
        SettingMenuItem(modifier = modifier, onClick = {}, setTitle = "Accessibility")
    }
}