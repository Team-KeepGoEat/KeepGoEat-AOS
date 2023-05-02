package org.keepgoeat.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.data.service.NaverAuthService
import org.keepgoeat.databinding.ActivitySignBinding
import org.keepgoeat.presentation.base.screen.MixpanelActivity
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.onboarding.OnboardingActivity
import org.keepgoeat.util.UiState
import org.keepgoeat.util.extension.showToast
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity : MixpanelActivity<ActivitySignBinding>(R.layout.activity_sign, SCREEN_NAME) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService
    override val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.layoutKakaoSignIn.setOnClickListener {
            kakaoSignService.loginKakao(viewModel::login, viewModel::saveAccount)
        }
        binding.layoutNaverSignIn.setOnClickListener {
            naverSignService.loginNaver(viewModel::login, viewModel::saveAccount)
        }
    }

    private fun collectData() {
        viewModel.loginUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    showToast(getString(R.string.signin_success_toast_message))
                    moveToNext(it.data)
                }
                is UiState.Error -> {
                    showToast(getString(R.string.signin_failure_toast_message))
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    /** 가입자가 온보딩 모두 확인(생략 포함)하지 않은 경우 온보딩 화면으로 그 외의 경우(가입자가 온보딩에서 중간 이탈 + 로그인한 사용자)는 홈화면으로 이동 */
    /** @param onboardingFlag : first는 가입자 여부(true: 가입한 사용자, false: 로그인한 사용자), second는 온보딩 완료 버튼을 클릭했는지 여부 */
    private fun moveToNext(onboardingFlag: Pair<Boolean, Boolean>) {
        val nextScreen =
            if (onboardingFlag.first && !onboardingFlag.second) OnboardingActivity::class.java
            else HomeActivity::class.java
        startActivity(Intent(this, nextScreen))
        finish()
    }

    companion object {
        private const val SCREEN_NAME = "signup"
    }
}
