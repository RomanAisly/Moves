package com.films.ui.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.films.ui.components.AppLanguage
import java.util.Locale

// 1. Создаем глобальные переменные (как для темы)
val LocalAppLanguage = compositionLocalOf { AppLanguage.ENGLISH }
val LocalLanguageChangeHandler = compositionLocalOf<(AppLanguage) -> Unit> { {} }

@Composable
fun AppLanguageProvider(
    appLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    content: @Composable () -> Unit
) {
    // 2. Достаем код языка (например "ru" или "en")
    // Для Android ресурсов лучше использовать просто "ru" и "en", обрезая "-RU" и "-US"
    val localeCode = appLanguage.localeCode.split("-").first()
    val locale = Locale(localeCode)
    Locale.setDefault(locale)

    // 3. Создаем новую конфигурацию с новым языком
    val configuration = Configuration(LocalConfiguration.current)
    configuration.setLocale(locale)

    // 4. Создаем новый контекст с этой конфигурацией
    val context = LocalContext.current.createConfigurationContext(configuration)

    // 5. ПРОВАЙДИМ новый контекст и конфигурацию во всё приложение!
    CompositionLocalProvider(
        LocalContext provides context,
        LocalConfiguration provides configuration,
        LocalAppLanguage provides appLanguage,
        LocalLanguageChangeHandler provides onLanguageChange,
        content = content
    )
}