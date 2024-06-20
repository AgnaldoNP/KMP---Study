package dev.agnaldo.kmpsample.shared.repository.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpClient() = HttpClient(OkHttp) {
    installDefaultPlugins()
}
