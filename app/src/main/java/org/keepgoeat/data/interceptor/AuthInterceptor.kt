package org.keepgoeat.data.interceptor

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import org.keepgoeat.data.datasource.local.SignSharedPreferences
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localStorage: SignSharedPreferences,
    private val gson: Gson,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader("accesstoken", localStorage.accestToken).build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                // TODO 토큰 재발급 api 연동
            }
        }
        return response
    }
}
