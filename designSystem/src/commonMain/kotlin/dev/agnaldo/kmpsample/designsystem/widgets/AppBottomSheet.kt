@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.designsystem.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.shared.providers.Strings

@Composable
fun AppDialogBottomSheet(
    title: String,
    subtitle: String? = null,
    message: String? = null,
    okButtonText: String = Strings.ok.localize(),
    closeOnOkClick: Boolean = true,
    onOkClick: () -> Unit = {},
    cancelButtonText: String? = null,
    closeOnCancelClick: Boolean = true,
    onCancelClick: (() -> Unit)? = null,
    closeOnTouchOutside: Boolean = false
) {
    val isBottomSheetVisible = remember { mutableStateOf(false) }
    val isBackgroundVisible = remember { MutableTransitionState(true) }

    if (!isBackgroundVisible.currentState) return

    DisposableEffect(Unit) {
        isBottomSheetVisible.value = true
        onDispose { }
    }

    AnimatedVisibility(
        visibleState = isBackgroundVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) { detectTapGestures {} }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(AppTheme.colors.baseMiddle.copy(alpha = 0.5F))
                    .blur(1.dp)
                    .let {
                        if (closeOnTouchOutside) {
                            it.pointerInput(Unit) {
                                detectTapGestures {
                                    isBottomSheetVisible.value = false
                                    isBackgroundVisible.targetState = false
                                }
                            }
                        } else {
                            it
                        }
                    }
            )

            val enterTransition = slideInVertically(initialOffsetY = { it }) + fadeIn()
            val exitTransition = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            AnimatedVisibility(
                visible = isBottomSheetVisible.value,
                enter = enterTransition,
                exit = exitTransition
            ) {
                DialogBottomSheetContent(
                    title = title,
                    subtitle = subtitle,
                    message = message,
                    okButtonText = okButtonText,
                    onOkClick = {
                        if (closeOnOkClick) {
                            isBottomSheetVisible.value = false
                            isBackgroundVisible.targetState = false
                        }
                        onOkClick.invoke()
                    },
                    cancelButtonText = cancelButtonText,
                    onCancelClick = {
                        if (closeOnCancelClick) {
                            isBottomSheetVisible.value = false
                            isBackgroundVisible.targetState = false
                        }
                        onCancelClick?.invoke()
                    }
                )
            }
        }
    }
}

@Composable
private fun DialogBottomSheetContent(
    title: String,
    subtitle: String? = null,
    message: String? = null,
    okButtonText: String = Strings.ok.localize(),
    onOkClick: () -> Unit = {},
    cancelButtonText: String? = null,
    onCancelClick: (() -> Unit)? = null
) {
    val shape = AppTheme.shapes.medium.copy(
        bottomStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )
    Column(
        modifier = Modifier
            .clip(shape)
            .background(AppTheme.colors.surface)
            .border(
                border = AppTheme.borderStoke.small,
                shape = shape
            )
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 4.dp)
                .clip(AppTheme.shapes.medium)
                .background(AppTheme.colors.baseThin)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (message != null) {
            Text(
                text = message,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
        ) {
            if (cancelButtonText != null) {
                ButtonPrimary(
                    text = cancelButtonText,
                    modifier = Modifier.weight(1f),
                    onClick = { onCancelClick?.invoke() }
                )
            }
            ButtonPrimary(
                text = okButtonText,
                modifier = Modifier.weight(1f),
                onClick = onOkClick
            )
        }
    }
}
