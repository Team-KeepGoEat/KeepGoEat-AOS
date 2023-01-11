package org.keepgoeat.presentation

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.keepgoeat.R
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.databinding.ActivitySplashBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.sign.SignActivity
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(1000L)
            moveToNext()
            finish()
        }
    }

    private fun moveToNext() {
        if (KGEDataSource(this).isLogin)
            startActivity(Intent(this, HomeActivity::class.java))
        else
            startActivity(Intent(this, SignActivity::class.java))
    }
}
