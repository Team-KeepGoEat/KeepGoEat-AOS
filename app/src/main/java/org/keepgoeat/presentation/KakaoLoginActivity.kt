package org.keepgoeat.presentation

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityKakaoLoginBinding
import org.keepgoeat.util.binding.BindingActivity
import timber.log.Timber

class KakaoLoginActivity : BindingActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            KakaoSdk.init(this, this.getString(R.string.kakao_app_key))
            binding.btnKakaoLogin.setOnClickListener {
                loginKakao() //로그인
            }
    }

    private fun loginKakao() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Timber.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    Timber.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    moveMain()
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Timber.e(ContentValues.TAG, "카카오톡으로 로그인 실패", error)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Timber.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    moveMain()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun moveMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }
}
