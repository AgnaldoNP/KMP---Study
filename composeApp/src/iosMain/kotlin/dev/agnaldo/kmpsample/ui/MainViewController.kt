package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.window.ComposeUIViewController
import dev.agnaldo.kmpsample.ui.desktop.DesktopApp

@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController() = ComposeUIViewController {
//    MobileApp()
    DesktopApp()
}
