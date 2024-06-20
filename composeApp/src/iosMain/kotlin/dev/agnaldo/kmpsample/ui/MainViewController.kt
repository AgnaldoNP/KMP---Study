package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.window.ComposeUIViewController
import dev.agnaldo.kmpsample.mobile.MobileApp

@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController() = ComposeUIViewController {
    MobileApp()
//    DesktopApp()
}
