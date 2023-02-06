package org.keepgoeat.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class GenericError(val code: Int?, val message: String?) : ApiResult<Nothing>()
    object NetworkError : ApiResult<Nothing>()
}

fun <T> safeFlow(apiFunc: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Success(apiFunc.invoke()))
    } catch (e: HttpException) {
        emit(ApiResult.NetworkError)
    } catch (e: Exception) {
        emit(ApiResult.GenericError(e.hashCode(), e.message))
    } catch (e: Throwable) {
        emit(ApiResult.GenericError(e.hashCode(), e.message))
    }
}
