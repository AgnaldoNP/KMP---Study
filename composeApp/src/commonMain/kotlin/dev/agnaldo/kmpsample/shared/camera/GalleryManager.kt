package dev.agnaldo.kmpsample.shared.camera

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.image.SharedImage

@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager

expect class GalleryManager(
    onLaunch: @Composable () -> Unit
) {
    @Composable
    fun launch()
}
