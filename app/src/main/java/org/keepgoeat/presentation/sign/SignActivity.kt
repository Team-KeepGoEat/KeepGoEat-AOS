package org.keepgoeat.presentation.sign

import android.content.Intent
import android.os.Bundle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.SignService
import org.keepgoeat.databinding.ActivitySignBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.onboarding.OnboardingActivity
import org.keepgoeat.util.binding.BindingActivity
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    @Inject
    lateinit var signService: SignService

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e("카카오계정으로 로그인 실패 $error")
        } else if (token != null) {
            Timber.d("카카오계정으로 로그인 성공 ${token.accessToken}")
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.layoutSignIn.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                Timber.d("카카오톡 가능")
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    Timber.d("$token $error")
                    if (error != null) {
                        Timber.e("카카오톡으로 로그인 실패 $error")

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                Timber.d("카카오계정으로 가능")
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun moveMain() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
