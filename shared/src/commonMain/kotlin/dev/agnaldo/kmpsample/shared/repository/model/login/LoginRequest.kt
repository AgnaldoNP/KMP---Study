package dev.agnaldo.kmpsample.shared.repository.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("user") val username: String,
    @SerialName("pass") val password: String
)
