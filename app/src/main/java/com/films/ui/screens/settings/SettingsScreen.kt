package com.films.ui.screens.settings

import android.content.res.Configuration
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.films.core.di.previewModule
import com.films.ui.components.AppLanguage
import com.films.ui.components.AppTheme
import com.films.ui.components.BaseCard
import com.films.ui.components.BaseIcon
import com.films.ui.components.BaseText
import com.films.ui.theme.FilmsTheme
import com.films.ui.theme.LocalAppLanguage
import com.films.ui.theme.LocalAppTheme
import com.films.ui.theme.LocalLanguageChangeHandler
import com.films.ui.theme.LocalThemeChangeHandler
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

@Composable
fun SettingsScreen(paddingValues: PaddingValues) {

    val currentTheme = LocalAppTheme.current
    val onThemeChange = LocalThemeChangeHandler.current
    val currentLanguage = LocalAppLanguage.current
    val onLanguageChange = LocalLanguageChangeHandler.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BaseCard {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BaseText(
                        "Theme",
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BaseText(
                        "Language",
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
            .clickable(onClick = { onClick(option) }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseIcon(
            option.iconRes,
            iconTint = MaterialTheme.colorScheme.onBackground,
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
                selectedColor = MaterialTheme.colorScheme.surfaceDim,
                unselectedColor = MaterialTheme.colorScheme.onSurface
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
            .clickable(onClick = { onClick(option) }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BaseText(
            stringResource(option.titleRes),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.surfaceDim,
                unselectedColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun Preview() {
    val koin = remember {
        koinApplication {
            modules(previewModule)
        }.koin
    }
    KoinContext(context = koin) {
        FilmsTheme(onThemeChange = {}) {
            SettingsScreen(paddingValues = PaddingValues(0.dp))
        }
    }
}