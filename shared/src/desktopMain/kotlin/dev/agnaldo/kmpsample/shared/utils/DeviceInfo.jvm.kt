package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import dev.agnaldo.kmpsample.shared.di.ComponentInjector

@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val window = ComponentInjector.inject<ComposeWindow>()
    val windowWidth = window.size?.width ?: 0
    val windowHeight = window.size?.height ?: 0
    return windowWidth to windowHeight
}
