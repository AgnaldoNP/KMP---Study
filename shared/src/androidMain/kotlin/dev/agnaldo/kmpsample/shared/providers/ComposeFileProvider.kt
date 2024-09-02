package dev.agnaldo.kmpsample.shared.providers

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dev.agnaldo.kmpsample.shared.R
import java.io.File
import java.util.Objects

class ComposeFileProvider : FileProvider(
    R.xml.provider_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            // 1
            val tempFile = File.createTempFile(
                "picture_${System.currentTimeMillis()}", ".png", context.cacheDir
            ).apply {
                createNewFile()
            }
            // 2
            val authority = context.applicationContext.packageName + ".provider"
            // 3
            println("getImageUri: ${tempFile.absolutePath}")
            return getUriForFile(
                Objects.requireNonNull(context),
                authority,
                tempFile,
            )
        }
    }
}
