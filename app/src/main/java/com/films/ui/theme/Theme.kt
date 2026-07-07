package com.films.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.films.ui.components.SetTheme

@Immutable
data class AppColors(
    val screenBack: Color,
    val bottomBar: Color,
    val cardBack: Color,
    val cardBackSecondary: Color,
    val buttPrimary: Color,
    val buttSecondary: Color,
    val buttTertiary: Color,
    val iconBack: Color,
    val iconBackSecondary: Color,
    val iconBackTertiary: Color,
    val iconTint: Color,
    val iconTintSecondary: Color,
    val text: Color,
    val border: Color
)

val lightColors = AppColors(
    screenBack = mintCream,
    bottomBar = skyBlue.copy(alpha = 0.85f),
    cardBack = azure,
    cardBackSecondary = azure,
    buttPrimary = lightBlue,
    buttSecondary = archer,
    buttTertiary = white,
    iconBack = white,
    iconBackSecondary = white,
    iconBackTertiary = azure,
    iconTint = royalBlue,
    iconTintSecondary = royalBlue,
    text = black,
    border = lightSalmon
)

val darkColors = AppColors(
    screenBack = twilight,
    bottomBar = darkGray.copy(alpha = 0.85f),
    cardBack = teal,
    cardBackSecondary = darkStateBlue.copy(alpha = 0.9f),
    buttPrimary = darkOliveGreen.copy(alpha = 0.9f),
    buttSecondary = teal.copy(alpha = 0.9f),
    buttTertiary = gray,
    iconBack = darkGray,
    iconBackSecondary = indigo.copy(alpha = 0.8f),
    iconBackTertiary = white,
    iconTint = white,
    iconTintSecondary = lightBlue,
    text = white,
    border = yellow
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}

val LocalSetTheme = staticCompositionLocalOf {
    SetTheme.SYSTEM
}

val LocalThemeChangeHandler = staticCompositionLocalOf<(SetTheme) -> Unit> {
    {}
}

@Composable
fun FilmsTheme(
    setTheme: SetTheme = SetTheme.SYSTEM,
    onThemeChange: (SetTheme) -> Unit,
    content: @Composable () -> Unit
) {
    val isDark = when (setTheme) {
        SetTheme.LIGHT -> false
        SetTheme.DARK -> true
        SetTheme.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDark) darkColors else lightColors
    val animatedColorScheme = animateColorSchemeAsState(colorScheme)

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
        LocalSetTheme provides setTheme,
        LocalThemeChangeHandler provides onThemeChange,
        LocalAppColors provides animatedColorScheme
    ) {
        MaterialTheme(
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

@Composable
private fun animateColorSchemeAsState(targetColor: AppColors): AppColors {
    val animationSpec = tween<Color>(durationMillis = 350)
    return AppColors(
        screenBack = animateColorAsState(targetColor.screenBack, animationSpec).value,
        bottomBar = animateColorAsState(targetColor.bottomBar, animationSpec).value,
        cardBack = animateColorAsState(targetColor.cardBack, animationSpec).value,
        cardBackSecondary = animateColorAsState(targetColor.cardBackSecondary, animationSpec).value,
        buttPrimary = animateColorAsState(targetColor.buttPrimary, animationSpec).value,
        buttSecondary = animateColorAsState(targetColor.buttSecondary, animationSpec).value,
        buttTertiary = animateColorAsState(targetColor.buttTertiary, animationSpec).value,
        iconBack = animateColorAsState(targetColor.iconBack, animationSpec).value,
        iconBackSecondary = animateColorAsState(targetColor.iconBackSecondary, animationSpec).value,
        iconBackTertiary = animateColorAsState(targetColor.iconBackTertiary, animationSpec).value,
        iconTint = animateColorAsState(targetColor.iconTint, animationSpec).value,
        iconTintSecondary = animateColorAsState(targetColor.iconTintSecondary, animationSpec).value,
        text = animateColorAsState(targetColor.text, animationSpec).value,
        border = animateColorAsState(targetColor.border, animationSpec).value
    )
}
