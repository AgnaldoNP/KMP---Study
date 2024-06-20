package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.agnaldo.kmpsample.mobile.MobileApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
//        DesktopApp()
        MobileApp()
    }
}
