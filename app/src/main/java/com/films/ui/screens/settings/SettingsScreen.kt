package com.films.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.films.ui.components.AppTheme
import com.films.ui.components.BaseIcon
import com.films.ui.components.BaseText
import com.films.ui.theme.LocalAppTheme
import com.films.ui.theme.LocalThemeChangeHandler
import com.films.ui.theme.indigo
import com.films.ui.theme.purple

@Composable
fun SettingsScreen(paddingValues: PaddingValues) {

    val currentTheme = LocalAppTheme.current
    val onThemeChange = LocalThemeChangeHandler.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseText("Choose theme")
        AppTheme.entries.forEach { theme ->
            ThemeItem(
                option = theme,
                isSelected = currentTheme == theme,
                onClick = { onThemeChange(theme) }
            )
        }
    }
}

@Composable
private fun ThemeItem(
    option: AppTheme,
    isSelected: Boolean,
    onClick: (AppTheme) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(option) })
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseIcon(
            option.iconRes,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        BaseText(
            stringResource(option.titleRes),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = purple,
                unselectedColor = indigo
            )
        )
    }
}