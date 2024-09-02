package dev.agnaldo.kmpsample.shared.image

import androidx.compose.ui.graphics.ImageBitmap

actual class SharedImage(private val bitmap: Any?) {
    actual fun toByteArray(): ByteArray? {
        return null
    }

    actual fun toImageBitmap(): ImageBitmap? {
        return null
    }
}
