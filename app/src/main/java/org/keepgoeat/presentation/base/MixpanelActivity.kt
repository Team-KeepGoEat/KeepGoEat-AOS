package org.keepgoeat.presentation.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import org.keepgoeat.presentation.common.MixpanelViewModel
import org.keepgoeat.util.binding.BindingActivity

abstract class MixpanelActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val screenName: String,
) : BindingActivity<B>(layoutRes) {
    abstract val viewModel: MixpanelViewModel

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(screenName)
    }
}
