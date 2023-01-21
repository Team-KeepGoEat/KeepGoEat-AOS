package org.keepgoeat.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.databinding.ktx.BuildConfig
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
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

    fun clear() {
        dataStore.edit {
            clear()
        }
    }

    companion object {
        const val FILE_NAME = "signSharedPreferences"
        const val ACCESS_TOKEN = "accessToken"
        const val IS_LOGIN = "isLogin"
        const val REFRESH_TOKEN = "refreshToken"
        const val IS_CLICKED_ONBOARDING_BUTTON = "isClickedOnboardingButton"
    }
}
