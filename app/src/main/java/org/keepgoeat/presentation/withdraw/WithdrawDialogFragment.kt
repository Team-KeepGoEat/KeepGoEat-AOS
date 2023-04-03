package org.keepgoeat.presentation.withdraw

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.data.service.NaverAuthService
import org.keepgoeat.databinding.DialogWithdrawBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.my.MyViewModel
import org.keepgoeat.presentation.type.LoginPlatformType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingDialogFragment
import org.keepgoeat.util.extension.showToast
import javax.inject.Inject

@AndroidEntryPoint
class WithdrawDialogFragment :
    BindingDialogFragment<DialogWithdrawBinding>(R.layout.dialog_withdraw) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService
    private val viewModel: MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvWithdraw.setOnClickListener {
            when (viewModel.loginPlatForm) {
                LoginPlatformType.NAVER -> naverSignService.deleteAccountNaver(viewModel::deleteAccount)
                LoginPlatformType.KAKAO -> kakaoSignService.deleteAccountKakao(viewModel::deleteAccount)
                else -> {}
            }
        }
    }

    private fun collectData() {
        viewModel.deleteAccountUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    requireActivity().showToast(getString(R.string.withdraw_success))
                    moveToSign()
                    dismiss()
                }
                is UiState.Error -> {
                    requireActivity().showToast(getString(R.string.withdraw_fail))
                    dismiss()
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSign() {
        Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(HomeActivity.ARG_KILL_HOME_AND_GO_TO_SIGN, true)
        }.also {
            startActivity(it)
        }
    }
}
