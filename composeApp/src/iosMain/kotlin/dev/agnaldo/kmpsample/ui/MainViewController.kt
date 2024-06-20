package dev.agnaldo.kmpsample.ui

import androidx.compose.ui.window.ComposeUIViewController
import dev.agnaldo.kmpsample.mobile.MobileApp
import dev.agnaldo.kmpsample.mobile.di.Modules.appComposeModule
import dev.agnaldo.kmpsample.shared.di.Modules.sharedModule
import org.koin.core.context.startKoin

@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(sharedModule, appComposeModule)
    }
    MobileApp()
//    DesktopApp()
}
