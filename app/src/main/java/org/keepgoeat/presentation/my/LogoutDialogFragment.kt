package org.keepgoeat.presentation.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.data.service.NaverAuthService
import org.keepgoeat.databinding.DialogLogoutBinding
import org.keepgoeat.presentation.type.LoginPlatformType
import org.keepgoeat.util.binding.BindingDialogFragment
import javax.inject.Inject

@AndroidEntryPoint
class LogoutDialogFragment : BindingDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService

    private val viewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addListeners()
        addListeners()
    }

    private fun addListeners() {
        binding.yes.setOnClickListener {
            when (viewModel.loginPlatForm) {
                LoginPlatformType.NAVER -> naverSignService.logoutNaver(viewModel::logout)
                LoginPlatformType.KAKAO -> kakaoSignService.logoutKakao(viewModel::logout)
                else -> {}
            }
            dismiss()
        }
        binding.no.setOnClickListener {
            dismiss()
        }
    }
}
