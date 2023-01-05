package org.keepgoeat.presentation

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivitySplashBinding
import org.keepgoeat.presentation.dummy.DummyActivity
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(2000)
            moveToNext()
        }
    }

    private fun moveToNext() {
        startActivity(Intent(this, DummyActivity::class.java))
    }
}
