package dev.agnaldo.kmpsample.shared.camera

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.image.SharedImage

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CameraManager actual constructor(
    private val onLaunch: @Composable () -> Unit
) {
    @Composable
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    return CameraManager {}
}
