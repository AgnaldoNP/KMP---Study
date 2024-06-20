package dev.agnaldo.kmpsample.shared.providers
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

class IosLanguage : Language {
    override val name: String = NSLocale.currentLocale.languageCode
}

actual fun getLanguage(): Language = IosLanguage()
