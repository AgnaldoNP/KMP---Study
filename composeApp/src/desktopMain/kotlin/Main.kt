import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.agnaldo.kmpsample.ui.desktop.DesktopApp
import dev.agnaldo.kmpsample.ui.mobile.MobileApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            size = DpSize(420.dp, 900.dp),
        ),
        title = "KotlinProject",
    ) {
        DesktopApp()
//        MobileApp()
    }
}
