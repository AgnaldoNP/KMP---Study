package dev.agnaldo.kmpsample.designsystem.colors

import androidx.compose.ui.graphics.Color

internal object FlavorColors {

    internal val lightColors = AppColors(
        isLight = true,
        primary = Color(0xFFFFFFFF),
        primaryLight = Color(0xFF1A45A3),
        primaryDark = Color(0xFF0F1561),
        onPrimary = Color(0xFF000000),

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

    internal val darkColors = lightColors.copy()
}
