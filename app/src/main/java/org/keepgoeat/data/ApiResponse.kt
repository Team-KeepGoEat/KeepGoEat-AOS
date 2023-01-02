package org.keepgoeat.data

sealed class ApiResponse<out T>(val data: T?) { // TODO 수정
    data class Idle<T>(val datas: T?) : ApiResponse<T>(null)
    data class Loading<T>(val datas: T?) : ApiResponse<T>(datas)
    data class Success<T>(val datas: T) : ApiResponse<T>(datas)
    data class Failure(val throwable: Throwable?) : ApiResponse<Nothing>(null)
}
