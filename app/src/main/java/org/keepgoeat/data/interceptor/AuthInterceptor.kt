package org.keepgoeat.data.interceptor

import android.app.Application
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.keepgoeat.BuildConfig
import org.keepgoeat.R
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.util.extension.showToast
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val localStorage: KGEDataSource,
    private val context: Application,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = originalRequest.newAuthBuilder().build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                val refreshTokenRequest = originalRequest.newBuilder().get()
                    .url("${BuildConfig.KGE_BASE_URL}auth/refresh")
                    .addHeader(ACCESS_TOKEN, localStorage.accessToken)
                    .addHeader(REFRESH_TOKEN, localStorage.refreshToken)
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val responseRefresh =
                        json.decodeFromString<ResponseRefresh>(
                            refreshTokenResponse.body?.string()
                                ?: throw IllegalStateException("refreshTokenResponse is null $refreshTokenResponse")
                        )

                    with(localStorage) {
                        accessToken = responseRefresh.data.accessToken
                        refreshToken = responseRefresh.data.refreshToken
                    }
                    refreshTokenResponse.close()

                    val newRequest = originalRequest.newAuthBuilder().build()
                    return chain.proceed(newRequest)
                } else {
                    with(context) {
                        CoroutineScope(Dispatchers.Main).launch {
                            startActivity(
                                Intent.makeRestartActivityTask(
                                    packageManager.getLaunchIntentForPackage(packageName)?.component
                                )
                            )
                            showToast(getString(R.string.auto_login_failure))
                            localStorage.clear()
                        }
                    }
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder()
            .addHeader(ACCESS_TOKEN, localStorage.accessToken)
            .addHeader(REFRESH_TOKEN, localStorage.refreshToken)

    companion object {
        private const val ACCESS_TOKEN = "accesstoken"
        private const val REFRESH_TOKEN = "refreshtoken"
    }
}
