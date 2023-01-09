package org.keepgoeat.util

import org.keepgoeat.data.ApiResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> Response<T>): ApiResult<T?> {
    return runCatching {
        ApiResult.Success(apiCall().body())
    }.getOrElse { throwable ->
        when (throwable) {
            is IOException -> ApiResult.NetworkError
            is HttpException -> ApiResult.GenericError(throwable.code(), throwable.message)
            else -> ApiResult.GenericError(null, throwable.message)
        }
    }
}

fun <T> List<T>.toUiState() =
    if (this.isEmpty()) UiState.Empty else UiState.Success(this)
