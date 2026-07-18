package com.films.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.films.AppLanguage
import com.films.AppTheme
import com.films.components.BaseCard
import com.films.components.BaseIcon
import com.films.components.BaseText
import com.films.components.getIconRes
import com.films.components.getTitleRes
import com.films.theme.BaseTheme
import com.films.theme.LocalLanguageChangeHandler
import com.films.theme.LocalSetLanguage
import com.films.theme.LocalSetTheme
import com.films.theme.LocalThemeChangeHandler
import com.films.ui.R

@Composable
fun SettingsScreen(paddingValues: PaddingValues) {

    val currentTheme = LocalSetTheme.current
    val onThemeChange = LocalThemeChangeHandler.current
    val currentLanguage = LocalSetLanguage.current
    val onLanguageChange = LocalLanguageChangeHandler.current

    Column(
        Modifier
            .fillMaxSize()
            .background(BaseTheme.colors.screenBack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding() + 16.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BaseCard {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    BaseText(
                        stringResource(R.string.theme),
                        textAlign = TextAlign.Center,
                        textStyle = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    AppTheme.entries.forEach { theme ->
                        ThemeItem(
                            option = theme,
                            isSelected = currentTheme == theme,
                            onClick = { onThemeChange(theme) }
                        )
                    }
                }
            }

            BaseCard {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    BaseText(
                        stringResource(R.string.language),
                        textAlign = TextAlign.Center,
                        textStyle = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    AppLanguage.entries.forEach { language ->
                        LanguageItem(
                            option = language,
                            isSelected = currentLanguage == language,
                            onClick = { onLanguageChange(language) }
                        )
                    }
                }
            }
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
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = { onClick(option) })
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseIcon(
            option.getIconRes(),
            iconTint = BaseTheme.colors.iconTint,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        BaseText(
            stringResource(option.getTitleRes()),
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = BaseTheme.colors.iconTintSecondary,
                unselectedColor = BaseTheme.colors.iconTint
            )
        )
    }
}

@Composable
private fun LanguageItem(
    option: AppLanguage,
    isSelected: Boolean,
    onClick: (AppLanguage) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = { onClick(option) })
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseText(
            stringResource(option.getTitleRes()),
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = BaseTheme.colors.iconTintSecondary,
                unselectedColor = BaseTheme.colors.iconTint
            )
        )
    }
}