package dev.agnaldo.kmpsample.shared.repository.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun createHttpClient(): HttpClient

fun HttpClientConfig<*>.installCustomInterceptor() {
    install("CustomRequestInterceptor") {
        requestPipeline.intercept(HttpRequestPipeline.Before) {
            context.header("Content-Type", "application/json; charset=utf-8")
            context.header("Accept", "application/json")
            context.header("KmpHeaderTest", "Testando 1234")
        }
    }
}

fun HttpClientConfig<*>.installDefaultPlugins() {
    install(Logging) {
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    installCustomInterceptor()
}
