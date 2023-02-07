package org.keepgoeat.presentation.sign

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.data.service.NaverAuthService
import org.keepgoeat.databinding.ActivitySignBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.onboarding.OnboardingActivity
import org.keepgoeat.util.binding.BindingActivity
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.layoutKakaoSignIn.setOnClickListener {
            kakaoSignService.loginKakao(::moveToNext)
        }
        binding.layoutNaverSignIn.setOnClickListener {
            naverSignService.loginNaver(::moveToNext)
        }
    }

    private fun moveToNext(isFirst: Boolean, isClicked: Boolean) {
        Timber.d("isFirst : $isFirst !isClicked : ${!isClicked}")
        val nextScreen =
            if (isFirst && !isClicked) OnboardingActivity::class.java
            else HomeActivity::class.java
        startActivity(Intent(this, nextScreen))
        finish()
    }
}
