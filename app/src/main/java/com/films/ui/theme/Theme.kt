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
    surface = lightYellow.copy(alpha = 0.6f),
    onSurface = lightBlue,
    surfaceContainer = skyBlue.copy(alpha = 0.85f),
    surfaceVariant = azure,
    onSurfaceVariant = white,
    surfaceTint = snow,
    onBackground = black,
    primary = lightBlue,
    primaryContainer = archer,
    secondary = white,
    outline = lightSalmon
)

private val DarkColorScheme = darkColorScheme(
    background = twilight,
    surface = lightYellow,
    onSurface = deepSkyBlue,
    surfaceContainer = silver.copy(alpha = 0.85f),
    surfaceVariant = gray,
    onSurfaceVariant = lightGray,
    surfaceTint = white,
    onBackground = white,
    primary = darkOliveGreen,
    secondary = gray,
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
    val animationSpec = tween<Color>(durationMillis = 500)
    return targetColorScheme.copy(
        background = animateColorAsState(targetColorScheme.background, animationSpec).value,
        surface = animateColorAsState(targetColorScheme.surface, animationSpec).value,
        onSurface = animateColorAsState(targetColorScheme.onSurface, animationSpec).value,
        surfaceContainer = animateColorAsState(
            targetColorScheme.surfaceContainer,
            animationSpec
        ).value,
        surfaceVariant = animateColorAsState(targetColorScheme.surfaceVariant, animationSpec).value,
        onSurfaceVariant = animateColorAsState(
            targetColorScheme.onSurfaceVariant,
            animationSpec
        ).value,
        surfaceTint = animateColorAsState(targetColorScheme.surfaceTint, animationSpec).value,
        onBackground = animateColorAsState(targetColorScheme.onBackground, animationSpec).value,
        primary = animateColorAsState(targetColorScheme.primary, animationSpec).value,
        secondary = animateColorAsState(targetColorScheme.secondary, animationSpec).value,
        outline = animateColorAsState(targetColorScheme.outline, animationSpec).value
    )
}
