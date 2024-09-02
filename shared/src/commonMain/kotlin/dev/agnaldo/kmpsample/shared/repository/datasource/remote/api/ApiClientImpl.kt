package dev.agnaldo.kmpsample.shared.repository.datasource.remote.api

import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.NetworkError
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.Result
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.performHttpRequest
import dev.agnaldo.kmpsample.shared.repository.model.login.LoginRequest
import dev.agnaldo.kmpsample.shared.repository.model.login.LoginSuccessResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiClientImpl(
    private val httpClient: HttpClient
) : ApiClient {
    private val baseUrl = "http://137.184.95.5/"

    override suspend fun login(user: String, pass: String): Result<LoginSuccessResponse, NetworkError> {
        return performHttpRequest<LoginSuccessResponse> {
            httpClient.post("$baseUrl/kmp/login") {
                setBody(LoginRequest(user, pass))
            }
        }
    }
}
