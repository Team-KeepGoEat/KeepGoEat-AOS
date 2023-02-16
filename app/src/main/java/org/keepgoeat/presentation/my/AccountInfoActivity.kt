package org.keepgoeat.presentation.my

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
import org.keepgoeat.databinding.ActivityAccountInfoBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.home.HomeActivity.Companion.ARG_KILL_HOME_AND_GO_TO_SIGN
import org.keepgoeat.presentation.type.SocialLoginType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import javax.inject.Inject

@AndroidEntryPoint
class AccountInfoActivity :
    BindingActivity<ActivityAccountInfoBinding>(R.layout.activity_account_info) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvLogout.setOnClickListener {
            LogoutDialogFragment().show(supportFragmentManager, "LogoutDialog")
        }
        binding.tvDeleteAccount.setOnClickListener {
            // TODO 탈퇴 화면으로 이동
            when (viewModel.loginPlatForm) {
                SocialLoginType.NAVER -> naverSignService.deleteAccountNaver(viewModel::deleteAccount)
                SocialLoginType.KAKAO -> kakaoSignService.deleteAccountKakao(viewModel::deleteAccount)
                else -> {}
            }
        }
    }

    private fun collectData() {
        viewModel.logoutUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    moveToSign()
                }
                is UiState.Error -> {
                    // TODO 로그아웃 실패 시 예외 처리
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.deleteAccountUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    moveToSign()
                }
                is UiState.Error -> {
                    // TODO 회원 탈퇴 실패 시 예외 처리
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSign() {
        Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(ARG_KILL_HOME_AND_GO_TO_SIGN, true)
        }.also {
            startActivity(it)
        }
    }
}
