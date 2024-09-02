package dev.agnaldo.kmpsample.shared.camera

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.image.SharedImage

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    return GalleryManager {
        onResult.invoke(null)
    }
}

actual class GalleryManager actual constructor(
    private val onLaunch: @Composable () -> Unit
) {
    @Composable
    actual fun launch() {
        onLaunch()
    }
}
