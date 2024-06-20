package dev.agnaldo.kmpsample.shared.repository.feature.login

import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.NetworkError
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.Result
import dev.agnaldo.kmpsample.shared.repository.model.login.LoginSuccessResponse

interface LoginRepository {
    suspend fun login(user: String, pass: String): Result<LoginSuccessResponse, NetworkError>
}
