package org.keepgoeat.presentation.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityOnboardingBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class OnBoardingActivity : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val viewModel: OnBoardingViewModel by viewModels()
    private val mockOnboardingList = listOf(
        OnBoardingItem(
            title = R.string.onboarding1_title,
            des = R.string.onboarding1_des,
            image = R.drawable.img_onboarding_1
        ),
        OnBoardingItem(
            title = R.string.onboarding2_title,
            des = R.string.onboarding2_des,
            image = R.drawable.img_onboarding_2
        ),
        OnBoardingItem(
            R.string.onboarding3_title,
            des = R.string.onboarding3_des,
            image = R.drawable.img_onboarding_3
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val adapter = OnBoardingAdapter(this)
        val viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false
        viewPager.adapter = adapter
        adapter.setOnBoardingList(mockOnboardingList)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setPosition(position)
            }
        })
        TabLayoutMediator(binding.indicator, viewPager) { tab, position -> }.attach()
    }

    private fun addListeners() {
        binding.btnNext.setOnClickListener {
            binding.viewPager.currentItem++
        }
    }
}
