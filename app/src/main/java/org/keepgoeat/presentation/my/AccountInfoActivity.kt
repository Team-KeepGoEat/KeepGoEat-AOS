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
import org.keepgoeat.domain.model.UserInfo
import org.keepgoeat.presentation.base.screen.MixpanelActivity
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.home.HomeActivity.Companion.ARG_KILL_HOME_AND_GO_TO_SIGN
import org.keepgoeat.presentation.my.MyActivity.Companion.ARG_USER_INFO
import org.keepgoeat.presentation.my.withdraw.WithdrawActivity
import org.keepgoeat.util.UiState
import org.keepgoeat.util.extension.getParcelable
import org.keepgoeat.util.extension.showToast
import javax.inject.Inject

@AndroidEntryPoint
class AccountInfoActivity :
    MixpanelActivity<ActivityAccountInfoBinding>(R.layout.activity_account_info, SCREEN_NAME) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService
    override val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.getParcelable(ARG_USER_INFO, UserInfo::class.java)?.let { // TODO need refactoring
            binding.tvUserName.text = it.name
            binding.tvUserEmail.text = it.email
        }

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvLogout.setOnClickListener {
            LogoutDialogFragment().show(supportFragmentManager, "logoutDialog")
        }
        binding.tvDeleteAccount.setOnClickListener {
            val intent = Intent(this, WithdrawActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun collectData() {
        viewModel.logoutUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    showToast(getString(R.string.my_logout_success_toast_message))
                    moveToSign()
                }
                is UiState.Error -> {
                    showToast(getString(R.string.my_logout_failure_toast_message))
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

    companion object {
        private const val SCREEN_NAME = "account"
    }
}
