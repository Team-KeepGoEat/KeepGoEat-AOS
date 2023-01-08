package org.keepgoeat.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.SignService
import org.keepgoeat.databinding.ActivitySignBinding
import org.keepgoeat.presentation.onboarding.OnboardingActivity
import org.keepgoeat.util.binding.BindingActivity
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    private val viewModel: SignViewModel by viewModels()
    @Inject
    lateinit var signService: SignService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        KakaoSdk.init(this, this.getString(R.string.kakao_app_key1))
        addListeners()
    }

    private fun addListeners() {
        binding.layoutSignIn.setOnClickListener {
            signService.loginKakao(::moveToOnBoarding)
//            viewModel.login(::moveToOnBoarding)
        }
    }

    private fun moveToOnBoarding(){
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }
}
