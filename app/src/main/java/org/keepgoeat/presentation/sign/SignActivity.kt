package org.keepgoeat.presentation.sign

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.databinding.ActivitySignBinding
import org.keepgoeat.presentation.onboarding.OnboardingActivity
import org.keepgoeat.util.binding.BindingActivity
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    @Inject
    lateinit var signService: KakaoAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.layoutSignIn.setOnClickListener {
            signService.loginKakao(::moveToOnBoarding)
        }
    }

    private fun moveToOnBoarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }
}
