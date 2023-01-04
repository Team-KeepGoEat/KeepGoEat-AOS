package org.keepgoeat.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivitySplashBinding
import org.keepgoeat.presentation.dummy.DummyActivity
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class SplashActivity: BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this,DummyActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}