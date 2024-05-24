@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.designsystem.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme

private val defaultModifiers = Modifier.padding(0.dp).defaultMinSize(
    minWidth = ButtonDefaults.MinWidth,
    minHeight = 36.dp
)

@Composable
internal fun AppButton(
    text: String? = null,
    onClick: () -> Unit,
    colors: ButtonColors,
    modifier: Modifier? = null,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    contentPadding: PaddingValues = PaddingValues(all = 2.dp),
    content: @Composable RowScope.() -> Unit
) {
    val currentModifier = if (modifier != null) {
        defaultModifiers.then(modifier)
    } else {
        defaultModifiers
    }

    return Button(
        onClick = onClick,
        modifier = currentModifier,
        enabled = enabled,
        colors = colors,
        border = border,
        elevation = elevation,
        shape = shape,
        contentPadding = contentPadding,
    ) {
        if (text != null) {
            Text(
                text = text,
                style = AppTheme.typography.button,
                modifier = Modifier.padding(all = 10.dp)
            )
        }
        content()
    }

}

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier? = null,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {}
) {
    AppButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors(
            backgroundColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary,
            disabledBackgroundColor = AppTheme.colors.baseWeak,
            disabledContentColor = AppTheme.colors.baseMinimum
        )
    ) {
        content()
    }
}

@Composable
fun ButtonSecondary(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier? = null,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {}
) {
    AppButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors(
            backgroundColor = AppTheme.colors.secondary,
            contentColor = AppTheme.colors.onSecondary,
            disabledBackgroundColor = AppTheme.colors.baseWeak,
            disabledContentColor = AppTheme.colors.baseMinimum
        )
    ) {
        content()
    }
}

@Composable
fun ButtonContrast(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier? = null,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {}
) {
    AppButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors(
            backgroundColor = AppTheme.colors.contrast,
            contentColor = AppTheme.colors.onContrast,
            disabledBackgroundColor = AppTheme.colors.baseWeak,
            disabledContentColor = AppTheme.colors.baseMinimum
        )
    ) {
        content()
    }
}

@Composable
fun ButtonError(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier? = null,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {}
) {
    AppButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors(
            backgroundColor = AppTheme.colors.error,
            contentColor = AppTheme.colors.onError,
            disabledBackgroundColor = AppTheme.colors.baseWeak,
            disabledContentColor = AppTheme.colors.baseMinimum
        )
    ) {
        content()
    }
}

@Composable
fun ButtonGhost(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier? = null,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {}
) {
    val localModifier = Modifier.background(Color.Transparent)
    val currentModifier = if (modifier != null) {
        localModifier.then(modifier)
    } else {
        localModifier
    }

    val colors = buttonColors(
        backgroundColor = Color.Transparent,
        contentColor = AppTheme.colors.contrast,
        disabledBackgroundColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.baseWeak
    )
    val borderColor = colors.contentColor(enabled)

    AppButton(
        text = text,
        onClick = onClick,
        modifier = currentModifier,
        enabled = enabled,
        border = BorderStroke(1.dp, borderColor.value),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        colors = colors
    ) {
        content()
    }
}
