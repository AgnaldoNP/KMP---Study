import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

class MyApplicationState {
    val windows = mutableStateListOf<MyWindowState>()

    @Composable
    fun openNewWindow(
        id: String,
        window: @Composable () -> Unit
    ) {
        windows += MyWindowState(id, window)
    }

    fun exit() {
        windows.clear()
    }

    fun closeWindow(windowId: String) {
        windows.removeIf { it.id == windowId }
    }

    class MyWindowState(val id: String, val window: @Composable () -> Unit)
}
