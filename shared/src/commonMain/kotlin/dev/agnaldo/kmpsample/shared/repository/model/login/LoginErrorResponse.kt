package dev.agnaldo.kmpsample.shared.repository.model.login

import kotlinx.serialization.SerialName

data class LoginErrorResponse(
    @SerialName("error") val error: String
)
