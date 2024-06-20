package dev.agnaldo.kmpsample.shared.repository.datasource.remote.util

sealed interface Result<out D, out E : RequestError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : RequestError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : RequestError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E : RequestError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : RequestError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action.invoke(data)
            this
        }
    }
}
inline fun <T, E : RequestError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action.invoke(error)
            this
        }
        is Result.Success -> this
    }
}

inline fun <T, E : RequestError> Result<T, E>.subscribe(
    success: (T) -> Unit,
    error: (E) -> Unit
): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            error.invoke(this.error)
            this
        }
        is Result.Success -> {
            success.invoke(this.data)
            this
        }
    }
}

typealias EmptyResult<E> = Result<Unit, E>
