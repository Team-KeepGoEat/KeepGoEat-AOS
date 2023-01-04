package org.keepgoeat.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_PAGES = 3

class OnBoardingAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FragmentOnBoardingFirst()
            }
            1 -> {
                return FragmentOnBoardingSecond()
            }
            else -> {
                return FragmentOnBoardingThird()
            }
        }
    }
}
