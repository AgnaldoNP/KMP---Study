@file:Suppress("ktlint:standard:function-naming")

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.shared.providers.Strings

@Composable
fun FullScreenLoading(message: String? = Strings.loading.localize()) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(AppTheme.colors.background.copy(alpha = 0.7f))
            .pointerInput(Unit) { detectTapGestures { /* Do nothing here to consume the click events */ } },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = AppTheme.colors.secondary)
            message?.let { Text(it) }
        }
    }
}
