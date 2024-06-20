package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.agnaldo.kmpsample.mobile.MobileApp
import dev.agnaldo.kmpsample.mobile.di.Modules.appComposeModule
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import dev.agnaldo.kmpsample.shared.di.Modules.sharedModule
import kotlinx.browser.window
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(sharedModule, appComposeModule)
    }
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        ComponentInjector.loadInjectableComponent(window)
//        DesktopApp()
        MobileApp()
    }
}
