@file:Suppress("ktlint:standard:function-naming", "FunctionName")

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.agnaldo.kmpsample.mobile.MobileApp
import dev.agnaldo.kmpsample.mobile.di.Modules.appComposeModule
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import dev.agnaldo.kmpsample.shared.di.Modules.sharedModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(sharedModule, appComposeModule)
    }
    application {
        MainWindow().invoke()

        val applicationState = remember { MyApplicationState() }
        ComponentInjector.loadInjectableComponent(applicationState)

        for (window in applicationState.windows) {
            key(window) {
                window.window.invoke()
            }
        }
    }
}

private fun ApplicationScope.MainWindow() = @Composable {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            size = DpSize(420.dp, 900.dp),
        ),
        title = "KotlinProject"
    ) {
        ComponentInjector.loadInjectableComponent(this.window)
//                DesktopApp()
        MobileApp()
    }
}
