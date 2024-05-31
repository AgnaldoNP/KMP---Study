package dev.agnaldo.kmpsample.utils

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.format(vararg args: Any): String {
    val format = if (args.isEmpty()) this else this.replace("%s", "%@")
    return when (args.count()) {
        0 -> return format
        1 -> return NSString.stringWithFormat(format, args[0])
        2 -> return NSString.stringWithFormat(format, args[0], args[1])
        3 -> return NSString.stringWithFormat(format, args[0], args[1], args[2])
        4 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3])
        5 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4])
        6 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5])
        7 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6])
        8 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7])
        9 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8])
        else -> format
    }
}
