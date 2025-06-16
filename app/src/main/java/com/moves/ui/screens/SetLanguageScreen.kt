package com.moves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moves.ui.components.SettingMenuItem

@Composable
fun SetLangScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SettingMenuItem(onClick = {}, setTitle = "English")
        SettingMenuItem(onClick = {}, setTitle = "Russian")
        SettingMenuItem(onClick = {}, setTitle = "Ukrainian")
        SettingMenuItem(onClick = {}, setTitle = "German")
        SettingMenuItem(onClick = {}, setTitle = "French")
        SettingMenuItem(onClick = {}, setTitle = "Italian")
        SettingMenuItem(onClick = {}, setTitle = "Spanish")
        SettingMenuItem(onClick = {}, setTitle = "Portuguese")
        SettingMenuItem(onClick = {}, setTitle = "Korean")
        SettingMenuItem(onClick = {}, setTitle = "Japanese")
        SettingMenuItem(onClick = {}, setTitle = "Chinese")
        SettingMenuItem(onClick = {}, setTitle = "Chinese Traditional")
        SettingMenuItem(onClick = {}, setTitle = "Arabic")
        SettingMenuItem(onClick = {}, setTitle = "Turkish")

    }
}