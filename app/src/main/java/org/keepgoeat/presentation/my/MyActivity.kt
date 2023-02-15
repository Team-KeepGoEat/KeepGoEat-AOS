package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.data.service.KakaoAuthService
import org.keepgoeat.data.service.NaverAuthService
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.sign.SignActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.SocialLoginType
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import javax.inject.Inject

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    @Inject
    lateinit var naverSignService: NaverAuthService
    private val viewModel: MyViewModel by viewModels()
    private val goalAdapter = MyGoalAdapter()
    private val headerAdapter = MyHeaderAdapter(::getFilteredGoalWithEatingType)
    private val goalConcatAdapter = ConcatAdapter(headerAdapter, goalAdapter)
    private var isEnteredFromKeep: Boolean = false

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            moveToPrevious()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        isEnteredFromKeep = intent.getBooleanExtra(ARG_IS_ENTERED_FROM_KEEP, false)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        binding.rvGoalList.apply {
            adapter = goalConcatAdapter
            itemAnimator = null
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun getFilteredGoalWithEatingType(eatingType: EatingType?) {
        when (eatingType) {
            null -> viewModel.fetchAchievedGoalBySort(SortType.ALL)
            EatingType.MORE -> viewModel.fetchAchievedGoalBySort(SortType.MORE)
            EatingType.LESS -> viewModel.fetchAchievedGoalBySort(SortType.LESS)
        }
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            moveToPrevious()
        }
        binding.tvLogout.setOnClickListener {
            when (viewModel.loginPlatForm) {
                SocialLoginType.NAVER -> naverSignService.logoutNaver(viewModel::logout)
                SocialLoginType.KAKAO -> kakaoSignService.logoutKakao(viewModel::logout)
                else -> {}
            }
        }
        binding.tvDeleteAccount.setOnClickListener {
            when (viewModel.loginPlatForm) {
                SocialLoginType.NAVER -> naverSignService.deleteAccountNaver(viewModel::deleteAccount)
                SocialLoginType.KAKAO -> kakaoSignService.deleteAccountKakao(viewModel::deleteAccount)
                else -> {}
            }
        }
    }

    private fun collectData() {
        viewModel.achievedGoalUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    goalAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {} // TODO state에 따른 ui 업데이트 필요시 작성
                is UiState.Loading -> {}
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.logoutUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    startActivity(Intent(this, SignActivity::class.java))
                    finish()
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
                    startActivity(Intent(this, SignActivity::class.java))
                    finish()
                }
                is UiState.Error -> {
                    // TODO 회원 탈퇴 실패 시 예외 처리
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun moveToPrevious() {
        if (isEnteredFromKeep) moveToHome()
        else finish()
    }

    companion object {
        const val ARG_IS_ENTERED_FROM_KEEP = "isEnteredFromKeep"
    }
}
