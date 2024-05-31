package dev.agnaldo.kmpsample.providers

import java.util.Locale

class JvmLanguage : Language {
    override val name: String = Locale.getDefault().language
}

actual fun getLanguage(): Language = JvmLanguage()
