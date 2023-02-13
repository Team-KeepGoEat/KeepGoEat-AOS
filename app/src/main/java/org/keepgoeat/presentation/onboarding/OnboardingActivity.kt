package org.keepgoeat.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityOnboardingBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.util.binding.BindingActivity

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
        collectData()
    }

    private fun initLayout() {
        val adapter = OnboardingAdapter(this)
        viewModel.setPosition(1)
        with(binding) {
            vpViewPager.adapter = adapter
            vpViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    this@OnboardingActivity.viewModel.setPosition(position)
                }
            })
            TabLayoutMediator(indicator, vpViewPager) { _, _ -> }.attach()
        }
    }

    private fun addListeners() {
        binding.btnNext.setOnClickListener {
            if (binding.vpViewPager.currentItem == 2) {
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

    private fun collectData() {
        viewModel.position.flowWithLifecycle(lifecycle).onEach { position ->
            when (position) {
                0 -> binding.btnNext.setText(R.string.onboarding1_button)
                1 -> binding.btnNext.setText(R.string.onboarding2_button)
                2 -> binding.btnNext.setText(R.string.onboarding3_button)
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
