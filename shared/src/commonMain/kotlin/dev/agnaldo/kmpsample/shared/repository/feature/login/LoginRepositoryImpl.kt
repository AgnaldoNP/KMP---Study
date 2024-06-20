package dev.agnaldo.kmpsample.shared.repository.feature.login

import dev.agnaldo.kmpsample.shared.repository.datasource.remote.api.ApiClient
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.NetworkError
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.Result
import dev.agnaldo.kmpsample.shared.repository.model.login.LoginSuccessResponse

class LoginRepositoryImpl(
    private val apiClient: ApiClient,
) : LoginRepository {
    override suspend fun login(user: String, pass: String): Result<LoginSuccessResponse, NetworkError> {
        return apiClient.login(user, pass)
    }
}
