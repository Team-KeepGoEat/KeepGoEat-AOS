package org.keepgoeat.presentation.kakaologin

import android.content.Intent
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.LoginService
import org.keepgoeat.databinding.ActivityKakaoLoginBinding
import org.keepgoeat.presentation.MainActivity
import org.keepgoeat.util.binding.BindingActivity
import javax.inject.Inject

@AndroidEntryPoint
class KakaoLoginActivity : BindingActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login){
    //private val viewModel: KakaoLoginViewModel by viewModels()

    @Inject
    lateinit var kakaoAuthService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding.viewModel = viewModel
        //binding.lifecycleOwner = this
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))
        addListeners()
    }

    private fun addListeners() {
        binding.btnKakaoLogin.setOnClickListener {
            kakaoAuthService.loginKakao()
            //moveMain()
        }
    }

    private fun moveMain(){
        val intent = Intent(this@KakaoLoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
