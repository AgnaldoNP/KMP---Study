package dev.agnaldo.kmpsample.utils

actual fun String.format(vararg args: Any): String {
    var result = this
    args.forEach { arg ->
        result = result.replaceFirst("%s", arg.toString())
    }
    return result
}
