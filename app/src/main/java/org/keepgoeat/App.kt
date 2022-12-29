package org.keepgoeat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.keepgoeat.util.KGEDebugTree
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(KGEDebugTree())
    }
}