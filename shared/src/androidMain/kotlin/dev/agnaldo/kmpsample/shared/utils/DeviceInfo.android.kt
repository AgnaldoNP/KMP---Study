package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return screenWidth to screenHeight
}
