package dev.agnaldo.kmpsample.shared.repository.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Suppress("CustomX509TrustManager", "TrustAllX509TrustManager")
private fun httpClientPlatformEngine(): HttpClientEngine {
    fun getTrustManager(): X509TrustManager {
        val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers.first() !is X509TrustManager)) {
            ("Unexpected default trust managers:" + trustManagers.contentToString())
        }
        return trustManagers.first() as X509TrustManager
    }

    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, getTrustManager())
        .hostnameVerifier { _: String?, _: SSLSession? -> true }
        .build()

    return OkHttp.create {
        config {
            preconfigured = okHttpClient
        }
    }
}

actual fun createHttpClient() = HttpClient(httpClientPlatformEngine()) {
    installDefaultPlugins()
}
