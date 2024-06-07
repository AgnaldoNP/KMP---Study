package dev.agnaldo.kmpsample.utils

actual fun String.format(vararg args: Any): String {
    return String.format(this, *args)
}
