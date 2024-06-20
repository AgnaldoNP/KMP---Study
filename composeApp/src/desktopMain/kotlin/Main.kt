import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.agnaldo.kmpsample.mobile.MobileApp
import dev.agnaldo.kmpsample.mobile.di.Modules.appComposeModule
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import dev.agnaldo.kmpsample.shared.di.Modules.sharedModule
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(sharedModule, appComposeModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            size = DpSize(420.dp, 900.dp),
        ),
        title = "KotlinProject"
    ) {
        ComponentInjector.loadInjectableComponent(this.window)
//        DesktopApp()
        MobileApp()
    }
}
