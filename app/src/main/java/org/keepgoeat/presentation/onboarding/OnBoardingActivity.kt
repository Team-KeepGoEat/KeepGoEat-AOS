package org.keepgoeat.presentation.onboarding

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityOnboardingBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class OnBoardingActivity : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = OnBoardingAdapter(this)
    }

    private fun addListeners() {
       binding.btnNext.setOnClickListener {
           binding.viewPager.currentItem++

           if(binding.viewPager.currentItem == 1){
               binding.btnNext.setText(R.string.onboarding2_button)
           }
           else{
               binding.btnNext.setText(R.string.onboarding3_button)
           }
       }
    }
}
