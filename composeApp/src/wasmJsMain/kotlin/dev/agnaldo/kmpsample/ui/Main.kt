package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.agnaldo.kmpsample.ui.desktop.DesktopApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { DesktopApp() }
}