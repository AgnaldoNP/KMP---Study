package dev.agnaldo.kmpsample.shared.repository.datasource.remote.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> performHttpRequest(
    blockRequest: () -> HttpResponse
): Result<T, NetworkError> {
    try {
        val response = blockRequest.invoke()
        return when (response.status.value) {
            in 200..299 -> {
                val loginSuccessResponse = response.body<T>()
                Result.Success(loginSuccessResponse)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        return Result.Error(NetworkError.UNKNOWN)
    }
}
