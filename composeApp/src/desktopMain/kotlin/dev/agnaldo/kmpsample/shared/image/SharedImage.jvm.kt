package dev.agnaldo.kmpsample.shared.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

actual class SharedImage(private val bufferedImage: BufferedImage) {
    actual fun toByteArray(): ByteArray? {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun toImageBitmap(): ImageBitmap? {
        return try {
            bufferedImage.toComposeImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
