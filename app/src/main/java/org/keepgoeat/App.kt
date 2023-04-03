package org.keepgoeat

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import org.keepgoeat.BuildConfig.KAKAO_NATIVE_KEY
import org.keepgoeat.util.KGEDebugTree
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(KGEDebugTree())
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
