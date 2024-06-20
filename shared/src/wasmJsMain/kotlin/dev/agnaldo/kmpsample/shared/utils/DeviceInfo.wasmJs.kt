package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import org.w3c.dom.Window

@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val window = ComponentInjector.inject<Window>()
    val width = window.innerWidth
    val height = window.innerHeight
    return width to height
}
