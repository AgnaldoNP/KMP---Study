package dev.agnaldo.kmpsample.designsystem.colors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

class AppColors(
    isLight: Boolean,
    primary: Color,
    primaryLight: Color,
    primaryDark: Color,
    onPrimary: Color,

    secondary: Color,
    secondaryLight: Color,
    secondaryDark: Color,
    onSecondary: Color,

    tertiary: Color,
    tertiaryLight: Color,
    tertiaryDark: Color,
    onTertiary: Color,

    contrast: Color,
    contrastLight: Color,
    contrastDark: Color,
    onContrast: Color,

    background: Color,
    backgroundLight: Color,
    backgroundDark: Color,
    onBackground: Color,

    surface: Color,
    surfaceLight: Color,
    surfaceDark: Color,
    onSurface: Color,

    error: Color,
    errorLight: Color,
    errorDark: Color,
    onError: Color,

    success: Color,
    successLight: Color,
    successDark: Color,
    onSuccess: Color,

    symbol: Color,
    text: Color,

    baseMinimum: Color,
    baseThin: Color,
    baseWeak: Color,
    baseMiddle: Color,
    baseStrong: Color,
    baseMaximum: Color,
) {

    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set

    var primaryLight by mutableStateOf(primaryLight, structuralEqualityPolicy())
        internal set

    var primaryDark by mutableStateOf(primaryDark, structuralEqualityPolicy())
        internal set

    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set

    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set

    var secondaryLight by mutableStateOf(secondaryLight, structuralEqualityPolicy())
        internal set

    var secondaryDark by mutableStateOf(secondaryDark, structuralEqualityPolicy())
        internal set

    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set

    var tertiary by mutableStateOf(tertiary, structuralEqualityPolicy())
        internal set

    var tertiaryLight by mutableStateOf(tertiaryLight, structuralEqualityPolicy())
        internal set

    var tertiaryDark by mutableStateOf(tertiaryDark, structuralEqualityPolicy())
        internal set

    var onTertiary by mutableStateOf(onTertiary, structuralEqualityPolicy())
        internal set

    var contrast by mutableStateOf(contrast, structuralEqualityPolicy())
        internal set

    var contrastLight by mutableStateOf(contrastLight, structuralEqualityPolicy())
        internal set

    var contrastDark by mutableStateOf(contrastDark, structuralEqualityPolicy())
        internal set

    var onContrast by mutableStateOf(onContrast, structuralEqualityPolicy())
        internal set

    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set

    var backgroundLight by mutableStateOf(backgroundLight, structuralEqualityPolicy())
        internal set

    var backgroundDark by mutableStateOf(backgroundDark, structuralEqualityPolicy())
        internal set

    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set

    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set

    var surfaceLight by mutableStateOf(surfaceLight, structuralEqualityPolicy())
        internal set

    var surfaceDark by mutableStateOf(surfaceDark, structuralEqualityPolicy())
        internal set

    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set

    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set

    var errorLight by mutableStateOf(errorLight, structuralEqualityPolicy())
        internal set

    var errorDark by mutableStateOf(errorDark, structuralEqualityPolicy())
        internal set

    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set

    var success by mutableStateOf(success, structuralEqualityPolicy())
        internal set

    var successLight by mutableStateOf(successLight, structuralEqualityPolicy())
        internal set

    var successDark by mutableStateOf(successDark, structuralEqualityPolicy())
        internal set

    var onSuccess by mutableStateOf(onSuccess, structuralEqualityPolicy())
        internal set

    var symbol by mutableStateOf(symbol, structuralEqualityPolicy())
        internal set

    var text by mutableStateOf(text, structuralEqualityPolicy())
        internal set

    var baseMinimum by mutableStateOf(baseMinimum, structuralEqualityPolicy())
        internal set

    var baseThin by mutableStateOf(baseThin, structuralEqualityPolicy())
        internal set

    var baseWeak by mutableStateOf(baseWeak, structuralEqualityPolicy())
        internal set

    var baseMiddle by mutableStateOf(baseMiddle, structuralEqualityPolicy())
        internal set

    var baseStrong by mutableStateOf(baseStrong, structuralEqualityPolicy())
        internal set

    var baseMaximum by mutableStateOf(baseMaximum, structuralEqualityPolicy())
        internal set

    internal fun copy(
        isLight: Boolean = this.isLight,
        primary: Color = this.primary,
        primaryLight: Color = this.primaryLight,
        primaryDark: Color = this.primaryDark,
        onPrimary: Color = this.onPrimary,

        secondary: Color = this.secondary,
        secondaryLight: Color = this.secondaryLight,
        secondaryDark: Color = this.secondaryDark,
        onSecondary: Color = this.onSecondary,

        tertiary: Color = this.tertiary,
        tertiaryLight: Color = this.tertiaryLight,
        tertiaryDark: Color = this.tertiaryDark,
        onTertiary: Color = this.onTertiary,

        contrast: Color = this.contrast,
        contrastLight: Color = this.contrastLight,
        contrastDark: Color = this.contrastDark,
        onContrast: Color = this.onContrast,

        background: Color = this.background,
        backgroundLight: Color = this.backgroundLight,
        backgroundDark: Color = this.backgroundDark,
        onBackground: Color = this.onBackground,

        surface: Color = this.surface,
        surfaceLight: Color = this.surfaceLight,
        surfaceDark: Color = this.surfaceDark,
        onSurface: Color = this.onSurface,

        error: Color = this.error,
        errorLight: Color = this.errorLight,
        errorDark: Color = this.errorDark,
        onError: Color = this.onError,

        symbol: Color = this.symbol,
        text: Color = this.text,

        baseMinimum: Color = this.baseMinimum,
        baseThin: Color = this.baseThin,
        baseWeak: Color = this.baseWeak,
        baseMiddle: Color = this.baseMiddle,
        baseStrong: Color = this.baseStrong,
        baseMaximum: Color = this.baseMaximum,
    ): AppColors = AppColors(
        isLight = isLight,
        primary = primary,
        primaryLight = primaryLight,
        primaryDark = primaryDark,
        onPrimary = onPrimary,

        secondary = secondary,
        secondaryLight = secondaryLight,
        secondaryDark = secondaryDark,
        onSecondary = onSecondary,

        tertiary = tertiary,
        tertiaryLight = tertiaryLight,
        tertiaryDark = tertiaryDark,
        onTertiary = onTertiary,

        contrast = contrast,
        contrastLight = contrastLight,
        contrastDark = contrastDark,
        onContrast = onContrast,

        background = background,
        backgroundLight = backgroundLight,
        backgroundDark = backgroundDark,
        onBackground = onBackground,

        surface = surface,
        surfaceLight = surfaceLight,
        surfaceDark = surfaceDark,
        onSurface = onSurface,

        error = error,
        errorLight = errorLight,
        errorDark = errorDark,
        onError = onError,

        success = success,
        successLight = successLight,
        successDark = successDark,
        onSuccess = onSuccess,

        symbol = symbol,
        text = text,

        baseMinimum = baseMinimum,
        baseThin = baseThin,
        baseWeak = baseWeak,
        baseMiddle = baseMiddle,
        baseStrong = baseStrong,
        baseMaximum = baseMaximum
    )

    internal fun updateColorsFrom(other: AppColors) {
        isLight = other.isLight
        primary = other.primary
        primaryLight = other.primaryLight
        primaryDark = other.primaryDark
        onPrimary = other.onPrimary

        secondary = other.secondary
        secondaryLight = other.secondaryLight
        secondaryDark = other.secondaryDark
        onSecondary = other.onSecondary

        tertiary = other.tertiary
        tertiaryLight = other.tertiaryLight
        tertiaryDark = other.tertiaryDark
        onTertiary = other.onTertiary

        contrast = other.contrast
        contrastLight = other.contrastLight
        contrastDark = other.contrastDark
        onContrast = other.onContrast

        background = other.background
        backgroundLight = other.backgroundLight
        backgroundDark = other.backgroundDark
        onBackground = other.onBackground

        surface = other.surface
        surfaceLight = other.surfaceLight
        surfaceDark = other.surfaceDark
        onSurface = other.onSurface

        error = other.error
        errorLight = other.errorLight
        errorDark = other.errorDark
        onError = other.onError

        success = other.success
        successLight = other.successLight
        successDark = other.successDark
        onSuccess = other.onSuccess

        symbol = other.symbol
        text = other.text

        baseMinimum = other.baseMinimum
        baseThin = other.baseThin
        baseWeak = other.baseWeak
        baseMiddle = other.baseMiddle
        baseStrong = other.baseStrong
        baseMaximum = other.baseMaximum
    }

    companion object {

        fun getColorsTheme(darkTheme: Boolean): AppColors {
            return if (darkTheme) FlavorColors.darkColors else FlavorColors.lightColors
        }
    }
}
