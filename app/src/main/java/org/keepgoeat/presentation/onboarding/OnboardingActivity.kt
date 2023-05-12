package org.keepgoeat.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityOnboardingBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.base.screen.BindingActivity

@AndroidEntryPoint
class OnboardingActivity :
    BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    override fun onStop() {
        super.onStop()
        stopRecodingScreenTime(viewModel.onboardingType.value.ordinal)
    }

    private fun initLayout() {
        with(binding) {
            vpViewPager.adapter = OnboardingAdapter(this@OnboardingActivity)
            vpViewPager.registerOnPageChangeCallback(getPageChangeCallback())
            TabLayoutMediator(indicator, vpViewPager) { _, _ -> }.attach()
        }
    }

    private fun getPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            var prevPos = viewModel.onboardingType.value.ordinal

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.setOnboardingType(position)
                recodeScreenTime(prevPos, position)
                prevPos = position
            }
        }

    private fun addListeners() {
        binding.btnNext.setOnClickListener {
            if (binding.vpViewPager.currentItem == viewModel.onboardingLastPos) {
                viewModel.setClickedOnboardingButton()
                moveToHome()
            }
            binding.vpViewPager.currentItem++
        }
        binding.tvSkip.setOnClickListener {
            viewModel.setClickedOnboardingButton()
            moveToHome()
        }
    }

    private fun moveToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    /** 이전 포지션 온보딩 뷰의 스크린타임 기록을 중단, 현재 포지션 온보딩 뷰의 스크린타임을을 기록하는 함수 (단, 초기 포지션이 0인 경우, if문을 실행하지 않음.) */
    private fun recodeScreenTime(prevPos: Int, curPos: Int) {
        if (prevPos != curPos) stopRecodingScreenTime(prevPos)
        viewModel.startRecodingScreenTime()
    }

    private fun stopRecodingScreenTime(pos: Int) {
        viewModel.stopRecodingScreenTime("$SCREEN_NAME ${pos + 1}")
    }

    companion object {
        private const val SCREEN_NAME = "onboarding"
    }
}
