package org.keepgoeat.data

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class GenericError(val code: Int?, val message: String?) : ApiResult<Nothing>()
    object NetworkError : ApiResult<Nothing>()
}
