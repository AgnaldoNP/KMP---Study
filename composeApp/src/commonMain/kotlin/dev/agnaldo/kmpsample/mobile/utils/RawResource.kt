package dev.agnaldo.kmpsample.mobile.utils

import kotlinproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

enum class RawResource(val path: String) {
    SPLASH("files/splash.json"),
    SHUTTER("files/shutter.wav");

    companion object {
        @OptIn(ExperimentalResourceApi::class)
        suspend fun RawResource.getTextContent(): String {
            return Res.readBytes(path).decodeToString()
        }
    }
}
