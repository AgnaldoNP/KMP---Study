package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val screenWidth = CGRectGetWidth(UIScreen.mainScreen.bounds).toInt()
    val screenHeight = CGRectGetHeight(UIScreen.mainScreen.bounds).toInt()
    return screenWidth to screenHeight
}
