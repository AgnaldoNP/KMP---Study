package dev.agnaldo.kmpsample.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import dev.agnaldo.kmpsample.designsystem.colors.AppColors
import dev.agnaldo.kmpsample.designsystem.typography.AppTypography

@Suppress("ktlint:standard:function-naming")
@Composable
fun ApplicationTheme(
    colors: AppColors = AppColors.getColorsTheme(isSystemInDarkTheme()),
    typography: AppTypography = AppTypography.default,
    content: @Composable () -> Unit
) {
    AppTheme.colors.updateColorsFrom(colors)
    AppTheme.typography.updateTypographyFrom(typography)

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
        ),
        typography = Typography(
            defaultFontFamily = AppTheme.typography.fontFamily,
            h1 = AppTheme.typography.h1,
            h2 = AppTheme.typography.h2,
            h3 = AppTheme.typography.h3,
            h4 = AppTheme.typography.h4,
            h5 = AppTheme.typography.h5,
            h6 = AppTheme.typography.h6,
            subtitle1 = AppTheme.typography.subtitle1,
            subtitle2 = AppTheme.typography.subtitle2,
            body1 = AppTheme.typography.body1,
            body2 = AppTheme.typography.body2,
            button = AppTheme.typography.button,
            caption = AppTheme.typography.caption,
            overline = AppTheme.typography.overline
        )
    ) {
        content()
    }
}

object AppTheme {
    private val currentColors = staticCompositionLocalOf { AppColors.lightColors.copy() }
    private val currentTypography = staticCompositionLocalOf { AppTypography.default.copy() }

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = currentColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = currentTypography.current
}
