package dev.agnaldo.kmpsample.shared.repository.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginSuccessResponse(
    @SerialName("name") val name: String,
    @SerialName("avatar") val avatar: String?,
    @SerialName("id") val id: String
)
