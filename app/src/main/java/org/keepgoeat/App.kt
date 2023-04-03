package org.keepgoeat

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import org.keepgoeat.BuildConfig.KAKAO_NATIVE_KEY
import org.keepgoeat.util.KGEDebugTree
import org.keepgoeat.util.mixpanel.MixpanelProvider
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var mixpanelProvider: MixpanelProvider

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(KGEDebugTree())
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
        mixpanelProvider.initialize(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
