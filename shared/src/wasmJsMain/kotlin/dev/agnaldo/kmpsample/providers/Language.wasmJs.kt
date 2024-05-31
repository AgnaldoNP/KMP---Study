package dev.agnaldo.kmpsample.providers

import kotlinx.browser.window

class WasmJsLanguage : Language {
    override val name: String = window.navigator.language
}

actual fun getLanguage(): Language = WasmJsLanguage()
