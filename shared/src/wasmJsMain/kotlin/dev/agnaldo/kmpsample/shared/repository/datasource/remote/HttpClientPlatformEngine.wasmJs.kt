package dev.agnaldo.kmpsample.shared.repository.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js
import io.ktor.client.engine.js.JsClientEngineConfig
import io.ktor.client.fetch.RequestInit
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey
import kotlinx.browser.window

class RequestInitWrapper(val requestInit: RequestInit)
val RequestInitWrapperKey = AttributeKey<RequestInitWrapper>("RequestInitWrapper")
fun jsObject(): RequestInit = js("({})")

fun HttpClientConfig<JsClientEngineConfig>.installCorsDisable() {
    install("CustomCors") {
        requestPipeline.intercept(HttpRequestPipeline.Before) {
            try {
                val requestInit: RequestInit = jsObject()
                requestInit.mode = "no-cors".toJsString()
                attributes.put(RequestInitWrapperKey, RequestInitWrapper(requestInit))
            } catch (e: Throwable) {
                window.alert(e.stackTraceToString())
            }
        }
    }
}

actual fun createHttpClient() = HttpClient(Js) {
    installDefaultPlugins()
    installCorsDisable()
}
