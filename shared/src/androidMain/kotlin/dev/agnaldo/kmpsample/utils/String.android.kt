package dev.agnaldo.kmpsample.utils

actual fun String.format(vararg args: Any): String {
    return this.format(*args)
}
