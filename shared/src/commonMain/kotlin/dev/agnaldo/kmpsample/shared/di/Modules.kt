package dev.agnaldo.kmpsample.shared.di

import dev.agnaldo.kmpsample.shared.repository.datasource.remote.api.ApiClient
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.api.ApiClientImpl
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.createHttpClient
import dev.agnaldo.kmpsample.shared.repository.feature.login.LoginRepository
import dev.agnaldo.kmpsample.shared.repository.feature.login.LoginRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object Modules {
    val sharedModule = module {
        single<HttpClient> { createHttpClient() }

        singleOf(::ApiClientImpl).bind<ApiClient>()
        singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    }
}
