package org.keepgoeat.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.databinding.ktx.BuildConfig
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import org.keepgoeat.presentation.type.SocialLoginType
import org.keepgoeat.util.safeValueOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KGEDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val dataStore: SharedPreferences =
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    var accessToken: String
        set(value) = dataStore.edit { putString(ACCESS_TOKEN, value) }
        get() = dataStore.getString(
            ACCESS_TOKEN,
            ""
        ) ?: ""

    var refreshToken: String
        set(value) = dataStore.edit { putString(REFRESH_TOKEN, value) }
        get() = dataStore.getString(
            REFRESH_TOKEN,
            ""
        ) ?: ""

    var isLogin: Boolean
        set(value) = dataStore.edit { putBoolean(IS_LOGIN, value) }
        get() = dataStore.getBoolean(IS_LOGIN, false)

    var isClickedOnboardingButton: Boolean
        set(value) = dataStore.edit { putBoolean(IS_CLICKED_ONBOARDING_BUTTON, value) }
        get() = dataStore.getBoolean(IS_CLICKED_ONBOARDING_BUTTON, false)

    var loginPlatform: SocialLoginType
        set(value) = dataStore.edit { putString(LOGIN_PLATFORM, value.name) }
        get() = safeValueOf<SocialLoginType>(dataStore.getString(LOGIN_PLATFORM, ""))
            ?: SocialLoginType.NONE

    var userEmail: String
        set(value) = dataStore.edit { putString(USER_EMAIL, value) }
        get() = dataStore.getString(USER_EMAIL, "") ?: ""

    var userName: String
        set(value) = dataStore.edit { putString(USER_NAME, value) }
        get() = dataStore.getString(USER_NAME, "") ?: ""

    /** 로그아웃 및 회원 탈퇴 시 유저의 데이터를 삭제하는 함수 (단, 로그아웃의 경우 IS_CLICKED_ONBOARDING_BUTTON은 제외하고 삭제한다. 재로그인 시 온보딩 띄우기를 방지하기 위함)*/
    fun clear(isWithdrawal: Boolean = false) {
        dataStore.edit {
            if (isWithdrawal) {
                clear()
            } else {
                remove(ACCESS_TOKEN)
                remove(REFRESH_TOKEN)
                remove(IS_LOGIN)
                remove(LOGIN_PLATFORM)
                remove(USER_NAME)
                remove(USER_EMAIL)
            }
        }
    }

    companion object {
        const val FILE_NAME = "signSharedPreferences"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        const val IS_LOGIN = "isLogin"
        const val IS_CLICKED_ONBOARDING_BUTTON = "isClickedOnboardingButton"
        const val LOGIN_PLATFORM = "loginPlatform"
        const val USER_EMAIL = "userEmail"
        const val USER_NAME = "userName"
    }
}
