package dev.agnaldo.kmpsample.designsystem.typography

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

class AppTypography(
    fontFamily: FontFamily,
    h1: TextStyle,
    h2: TextStyle,
    h3: TextStyle,
    h4: TextStyle,
    h5: TextStyle,
    h6: TextStyle,
    subtitle1: TextStyle,
    subtitle2: TextStyle,
    body1: TextStyle,
    body2: TextStyle,
    button: TextStyle,
    caption: TextStyle,
    overline: TextStyle
) {

    var fontFamily by mutableStateOf(fontFamily, structuralEqualityPolicy())
        internal set

    var h1 by mutableStateOf(h1, structuralEqualityPolicy())
        internal set

    var h2 by mutableStateOf(h2, structuralEqualityPolicy())
        internal set

    var h3 by mutableStateOf(h3, structuralEqualityPolicy())
        internal set

    var h4 by mutableStateOf(h4, structuralEqualityPolicy())
        internal set

    var h5 by mutableStateOf(h5, structuralEqualityPolicy())
        internal set

    var h6 by mutableStateOf(h6, structuralEqualityPolicy())
        internal set

    var subtitle1 by mutableStateOf(subtitle1, structuralEqualityPolicy())
        internal set

    var subtitle2 by mutableStateOf(subtitle2, structuralEqualityPolicy())
        internal set

    var body1 by mutableStateOf(body1, structuralEqualityPolicy())
        internal set

    var body2 by mutableStateOf(body2, structuralEqualityPolicy())
        internal set

    var button by mutableStateOf(button, structuralEqualityPolicy())
        internal set

    var caption by mutableStateOf(caption, structuralEqualityPolicy())
        internal set

    var overline by mutableStateOf(overline, structuralEqualityPolicy())
        internal set

    fun copy(
        fontFamily: FontFamily = this.fontFamily,
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        h3: TextStyle = this.h3,
        h4: TextStyle = this.h4,
        h5: TextStyle = this.h5,
        h6: TextStyle = this.h6,
        subtitle1: TextStyle = this.subtitle1,
        subtitle2: TextStyle = this.subtitle2,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2,
        button: TextStyle = this.button,
        caption: TextStyle = this.caption,
        overline: TextStyle = this.overline
    ): AppTypography = AppTypography(
        fontFamily = fontFamily,
        h1 = h1,
        h2 = h2,
        h3 = h3,
        h4 = h4,
        h5 = h5,
        h6 = h6,
        subtitle1 = subtitle1,
        subtitle2 = subtitle2,
        body1 = body1,
        body2 = body2,
        button = button,
        caption = caption,
        overline = overline
    )

    internal fun updateTypographyFrom(other: AppTypography) {
        fontFamily = other.fontFamily
        h1 = other.h1
        h1.withDefaultFontFamily(fontFamily)

        h2 = other.h2
        h2.withDefaultFontFamily(fontFamily)

        h3 = other.h3
        h3.withDefaultFontFamily(fontFamily)

        h4 = other.h4
        h4.withDefaultFontFamily(fontFamily)

        h5 = other.h5
        h5.withDefaultFontFamily(fontFamily)

        h6 = other.h6
        h6.withDefaultFontFamily(fontFamily)

        subtitle1 = other.subtitle1
        subtitle1.withDefaultFontFamily(fontFamily)

        subtitle2 = other.subtitle2
        subtitle2.withDefaultFontFamily(fontFamily)

        body1 = other.body1
        body1.withDefaultFontFamily(fontFamily)

        body2 = other.body2
        body2.withDefaultFontFamily(fontFamily)

        button = other.button
        button.withDefaultFontFamily(fontFamily)

        caption = other.caption
        caption.withDefaultFontFamily(fontFamily)

        overline = other.overline
        overline.withDefaultFontFamily(fontFamily)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AppTypography) return false

        if (fontFamily != other.fontFamily) return false
        if (h1 != other.h1) return false
        if (h2 != other.h2) return false
        if (h3 != other.h3) return false
        if (h4 != other.h4) return false
        if (h5 != other.h5) return false
        if (h6 != other.h6) return false
        if (subtitle1 != other.subtitle1) return false
        if (subtitle2 != other.subtitle2) return false
        if (body1 != other.body1) return false
        if (body2 != other.body2) return false
        if (button != other.button) return false
        if (caption != other.caption) return false
        if (overline != other.overline) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fontFamily.hashCode()
        result = 31 * result + h1.hashCode()
        result = 31 * result + h2.hashCode()
        result = 31 * result + h3.hashCode()
        result = 31 * result + h4.hashCode()
        result = 31 * result + h5.hashCode()
        result = 31 * result + h6.hashCode()
        result = 31 * result + subtitle1.hashCode()
        result = 31 * result + subtitle2.hashCode()
        result = 31 * result + body1.hashCode()
        result = 31 * result + body2.hashCode()
        result = 31 * result + button.hashCode()
        result = 31 * result + caption.hashCode()
        result = 31 * result + overline.hashCode()
        return result
    }

    companion object {
        val default = AppTypography(
            fontFamily = FontFamily.Default,
            h1 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Light,
                fontSize = 96.sp,
                lineHeight = 112.sp,
                letterSpacing = (-1.5).sp,
                fontFamily = FontFamily.Default
            ),
            h2 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Light,
                fontSize = 60.sp,
                lineHeight = 72.sp,
                letterSpacing = (-0.5).sp,
                fontFamily = FontFamily.Default
            ),
            h3 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 48.sp,
                lineHeight = 56.sp,
                letterSpacing = 0.sp,
                fontFamily = FontFamily.Default
            ),
            h4 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.25.sp,
                fontFamily = FontFamily.Default
            ),
            h5 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
                fontFamily = FontFamily.Default
            ),
            h6 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
                fontFamily = FontFamily.Default
            ),
            subtitle1 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
                fontFamily = FontFamily.Default
            ),
            subtitle2 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.1.sp,
                fontFamily = FontFamily.Default
            ),
            body1 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                fontFamily = FontFamily.Default
            ),
            body2 = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                fontFamily = FontFamily.Default
            ),
            button = DefaultTextStyle.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.25.sp,
                fontFamily = FontFamily.Default
            ),
            caption = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
                fontFamily = FontFamily.Default
            ),
            overline = DefaultTextStyle.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.5.sp,
                fontFamily = FontFamily.Default
            )
        )
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val DefaultLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None,
)

internal val DefaultTextStyle = TextStyle.Default.copy(
    lineHeightStyle = DefaultLineHeightStyle,
)
