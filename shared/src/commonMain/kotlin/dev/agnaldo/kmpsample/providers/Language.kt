package dev.agnaldo.kmpsample.providers

import dev.agnaldo.kmpsample.utils.format

interface Language {
    val name: String

    val supportedLanguage get() = SupportedLanguage.entries
        .firstOrNull { it.value == name }
        ?: SupportedLanguage.EN
}

data class LocalizedString(
    val pt: String,
    val en: String
) {
    fun localize(
        vararg args: Any,
        supportedLanguage: SupportedLanguage = getLanguage().supportedLanguage
    ): String {
        val value = when (supportedLanguage) {
            SupportedLanguage.PT -> pt
            SupportedLanguage.EN -> en
        }
        return value.format(*args)
    }
}

enum class SupportedLanguage(val value: String) {
    PT("pt"),
    EN("en"),
}

expect fun getLanguage(): Language
