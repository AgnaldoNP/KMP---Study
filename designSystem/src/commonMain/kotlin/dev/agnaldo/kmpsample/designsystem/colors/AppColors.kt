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
        isLight = this.isLight,
        primary = this.primary,
        primaryLight = this.primaryLight,
        primaryDark = this.primaryDark,
        onPrimary = this.onPrimary,

        secondary = this.secondary,
        secondaryLight = this.secondaryLight,
        secondaryDark = this.secondaryDark,
        onSecondary = this.onSecondary,

        tertiary = this.tertiary,
        tertiaryLight = this.tertiaryLight,
        tertiaryDark = this.tertiaryDark,
        onTertiary = this.onTertiary,

        contrast = this.contrast,
        contrastLight = this.contrastLight,
        contrastDark = this.contrastDark,
        onContrast = this.onContrast,

        background = this.background,
        backgroundLight = this.backgroundLight,
        backgroundDark = this.backgroundDark,
        onBackground = this.onBackground,

        surface = this.surface,
        surfaceLight = this.surfaceLight,
        surfaceDark = this.surfaceDark,
        onSurface = this.onSurface,

        error = this.error,
        errorLight = this.errorLight,
        errorDark = this.errorDark,
        onError = this.onError,

        success = this.success,
        successLight = this.successLight,
        successDark = this.successDark,
        onSuccess = this.onSuccess,

        symbol = this.symbol,
        text = this.text,

        baseMinimum = this.baseMinimum,
        baseThin = this.baseThin,
        baseWeak = this.baseWeak,
        baseMiddle = this.baseMiddle,
        baseStrong = this.baseStrong,
        baseMaximum = this.baseMaximum
    )

    internal fun updateColorsFrom(other: AppColors) {
        primary = other.primary
    }

    companion object {
        internal val lightColors = AppColors(
            isLight = true,
            primary = Color(0xFF132177),
            primaryLight = Color(0xFF1A45A3),
            primaryDark = Color(0xFF0F1561),
            onPrimary = Color(0xFFFFFFFF),

            secondary = Color(0xFFF7DC16),
            secondaryLight = Color(0xFFFFF569),
            secondaryDark = Color(0xFFDAAE0F),
            onSecondary = Color(0xFF132177),

            tertiary = Color(0xFFF7DC16),
            tertiaryLight = Color(0xFFFFF569),
            tertiaryDark = Color(0xFFDAAE0F),
            onTertiary = Color(0xFF132177),

            contrast = Color(0xFF2485EA),
            contrastLight = Color(0xFF61ADF0),
            contrastDark = Color(0xFF1C67B8),
            onContrast = Color(0xFFFFFFFF),

            background = Color(0xFFF8F8F8),
            backgroundLight = Color(0xFFF8F8F8), // TBD
            backgroundDark = Color(0xFFF8F8F8), // TBD
            onBackground = Color(0xFFF0F2F8),

            surface = Color(0xFFFFFFFF),
            surfaceLight = Color(0xFFFFFFFF), // TBD
            surfaceDark = Color(0xFFFFFFFF), // TBD
            onSurface = Color(0xFF80848A),

            error = Color(0xFFE51717),
            errorLight = Color(0xFFE51717), // TBD
            errorDark = Color(0xFFE51717), // TBD
            onError = Color(0xFFFFFFFF),

            success = Color(0xFF2CBF59),
            successLight = Color(0xFF71D18E),
            successDark = Color(0xFFE51717), // TBD
            onSuccess = Color(0xFFFFFFFF),

            symbol = Color(0xFF132177),
            text = Color(0xFFFF383838),

            baseMinimum = Color(0xFFFFFFFF),
            baseThin = Color(0xFFF0F2F8),
            baseWeak = Color(0xFFC3CBD9),
            baseMiddle = Color(0xFF80848A),
            baseStrong = Color(0xFF383838),
            baseMaximum = Color(0xFF000)
        )

        private val darkColors = lightColors.copy()

        fun getColorsTheme(darkTheme: Boolean): AppColors {
            return if (darkTheme) darkColors else lightColors
        }
    }
}
