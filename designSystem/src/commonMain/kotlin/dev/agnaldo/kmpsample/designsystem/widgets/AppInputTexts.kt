@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.designsystem.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme

@Composable
fun AppInputText(
    label: String,
    placeHolder: String = "",
    password: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                label,
                style = AppTheme.typography.body1,
                color = AppTheme.colors.primary,
                modifier = Modifier
                    .weight(3f)
                    .padding(end = 8.dp)
            )
            Box(modifier = Modifier.weight(7f)) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange.invoke(it)
                    },
                    singleLine = true,
                    maxLines = 1,
                    visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
                    decorationBox = @Composable { innerTextField ->
                        Box {
                            if (text.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    style = AppTheme.typography.caption,
                                    color = AppTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.padding(top = 8.dp))
        Line()
    }
}

@Composable
fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AppTheme.colors.baseWeak)
    )
}
