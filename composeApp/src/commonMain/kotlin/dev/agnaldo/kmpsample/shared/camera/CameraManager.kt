package dev.agnaldo.kmpsample.shared.camera

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.image.SharedImage

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(
    onLaunch: @Composable () -> Unit
) {
    @Composable
    fun launch()
}
