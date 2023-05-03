package org.keepgoeat.presentation.base.screen

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import org.keepgoeat.presentation.base.viewmodel.MixpanelViewModel

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
