package dev.agnaldo.kmpsample.shared.repository.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpClient() = HttpClient(Darwin) {
    installDefaultPlugins()
}
