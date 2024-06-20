package dev.agnaldo.kmpsample.shared.providers
import java.util.Locale

class AndroidLanguage : Language {
    override val name: String = Locale.getDefault().language
}

actual fun getLanguage(): Language = AndroidLanguage()
