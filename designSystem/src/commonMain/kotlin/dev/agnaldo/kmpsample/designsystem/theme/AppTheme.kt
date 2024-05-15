package dev.agnaldo.kmpsample.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import dev.agnaldo.kmpsample.designsystem.colors.AppColors

@Suppress("ktlint:standard:function-naming")
@Composable
fun ApplicationTheme(
    colors: AppColors = AppColors.getColorsTheme(isSystemInDarkTheme()),
    content: @Composable () -> Unit
) {
    AppTheme.colors.updateColorsFrom(colors)
    MaterialTheme(
        colors = Colors(
            primary = AppTheme.colors.primary,
            primaryVariant = AppTheme.colors.primaryDark,
            secondary = AppTheme.colors.secondary,
            secondaryVariant = AppTheme.colors.secondaryDark,
            background = AppTheme.colors.background,
            surface = AppTheme.colors.surface,
            error = AppTheme.colors.error,
            onPrimary = AppTheme.colors.onPrimary,
            onSecondary = AppTheme.colors.onSecondary,
            onBackground = AppTheme.colors.onBackground,
            onSurface = AppTheme.colors.onSurface,
            onError = AppTheme.colors.onError,
            isLight = AppTheme.colors.isLight
        )
    ) {
        content()
    }
}

object AppTheme {
    private val currentColors = staticCompositionLocalOf { AppColors.lightColors.copy() }

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = currentColors.current
}
