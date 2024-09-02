package dev.agnaldo.kmpsample.shared.camera

import androidx.compose.runtime.Composable
import dev.agnaldo.kmpsample.shared.image.SharedImage
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JFileChooser

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    return GalleryManager {
        val photo = pickPhoto()
        onResult.invoke(photo)
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

fun pickPhoto(): SharedImage? {
    val fileChooser = JFileChooser()
    val result = fileChooser.showOpenDialog(null)
    return if (result == JFileChooser.APPROVE_OPTION) {
        val file = fileChooser.selectedFile
        val bufferedImage: BufferedImage = ImageIO.read(file)
        SharedImage(bufferedImage)
    } else {
        null
    }
}
