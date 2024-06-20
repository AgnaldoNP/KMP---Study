package dev.agnaldo.kmpsample.shared.repository.datasource.remote.util

enum class NetworkError : RequestError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    NOT_FOUND,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}
