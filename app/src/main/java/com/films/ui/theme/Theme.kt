package com.films.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.films.ui.components.AppTheme

private val LightColorScheme = lightColorScheme(
    background = mintCream,
    surface = azure,
    onSurface = royalBlue,
    surfaceContainer = skyBlue.copy(alpha = 0.85f),
    onSurfaceVariant = white,
    surfaceTint = azure,
    surfaceDim = royalBlue,
    surfaceVariant = azure,
    onBackground = black,
    primary = lightBlue,
    primaryContainer = archer,
    secondary = white,
    tertiary = white,
    outline = lightSalmon
)

private val DarkColorScheme = darkColorScheme(
    background = twilight,
    surface = teal,
    onSurface = white,
    surfaceContainer = darkGray.copy(alpha = 0.85f),
    onSurfaceVariant = indigo.copy(alpha = 0.8f),
    surfaceTint = white,
    surfaceDim = lightBlue,
    surfaceVariant = darkStateBlue.copy(alpha = 0.9f),
    onBackground = white,
    primary = darkOliveGreen.copy(alpha = 0.9f),
    primaryContainer = teal.copy(alpha = 0.9f),
    secondary = gray,
    tertiary = darkGray,
    outline = yellow
)

val LocalAppTheme = staticCompositionLocalOf {
    AppTheme.SYSTEM
}

val LocalThemeChangeHandler = staticCompositionLocalOf<(AppTheme) -> Unit> {
    {}
}

@Composable
fun FilmsTheme(
    appTheme: AppTheme = AppTheme.SYSTEM,
    onThemeChange: (AppTheme) -> Unit,
    content: @Composable () -> Unit
) {
    val isDark = when (appTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    val targetColorScheme = if (isDark) DarkColorScheme else LightColorScheme
    val animatedColorScheme = animateColorSchemeAsState(targetColorScheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !isDark
            insetsController.isAppearanceLightNavigationBars = !isDark
        }
    }

    CompositionLocalProvider(
        LocalAppTheme provides appTheme,
        LocalThemeChangeHandler provides onThemeChange
    ) {
        MaterialTheme(
            colorScheme = animatedColorScheme,
            typography = Typography,
            content = content
        )
    }

}

@Composable
private fun animateColorSchemeAsState(targetColorScheme: ColorScheme): ColorScheme {
    val animationSpec = tween<Color>(durationMillis = 350)
    return targetColorScheme.copy(
        background = animateColorAsState(targetColorScheme.background, animationSpec).value,
        surface = animateColorAsState(targetColorScheme.surface, animationSpec).value,
        onSurface = animateColorAsState(targetColorScheme.onSurface, animationSpec).value,
        surfaceContainer = animateColorAsState(
            targetColorScheme.surfaceContainer,
            animationSpec
        ).value,
        onSurfaceVariant = animateColorAsState(
            targetColorScheme.onSurfaceVariant,
            animationSpec
        ).value,
        surfaceTint = animateColorAsState(targetColorScheme.surfaceTint, animationSpec).value,
        surfaceDim = animateColorAsState(targetColorScheme.surfaceDim, animationSpec).value,
        surfaceVariant = animateColorAsState(targetColorScheme.surfaceVariant, animationSpec).value,
        onBackground = animateColorAsState(targetColorScheme.onBackground, animationSpec).value,
        primary = animateColorAsState(targetColorScheme.primary, animationSpec).value,
        primaryContainer = animateColorAsState(
            targetColorScheme.primaryContainer,
            animationSpec
        ).value,
        secondary = animateColorAsState(targetColorScheme.secondary, animationSpec).value,
        tertiary = animateColorAsState(targetColorScheme.tertiary, animationSpec).value,
        outline = animateColorAsState(targetColorScheme.outline, animationSpec).value
    )
}
