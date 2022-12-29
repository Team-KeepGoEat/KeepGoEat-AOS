package org.keepgoeat.data

import java.io.IOException

sealed class NetworkState<out T> {
    // 200대 응답 성공한것
    data class Success<T>(val body: T) : NetworkState<T>()

    // isSuccessful 이 false인 경우(200~300대 응답이 아닌경우)
    data class Failure(val code: Int, val error: String?) : NetworkState<Nothing>()

    // onFailure로 넘어간경우(네트워크 오류,timeout 같은거)
    data class NetworkError(val error: IOException) : NetworkState<Nothing>()

    // 예상 못한에러(기타 모든 에러처리)
    data class UnknownError(val t: Throwable?, val errorState: String) : NetworkState<Nothing>()
}