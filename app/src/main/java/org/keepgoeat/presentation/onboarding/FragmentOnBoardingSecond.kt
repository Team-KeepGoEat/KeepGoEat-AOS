package org.keepgoeat.presentation.onboarding

import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.FragmentOnboarding2Binding
import org.keepgoeat.util.binding.BindingFragment

class FragmentOnBoardingSecond : BindingFragment<FragmentOnboarding2Binding>(R.layout.fragment_onboarding_2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

//val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
//binding.btnNext.setOnClickListener {
//    viewPager?.currentItem = 2
//}
