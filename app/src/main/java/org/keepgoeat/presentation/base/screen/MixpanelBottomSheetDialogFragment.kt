package org.keepgoeat.presentation.base.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.keepgoeat.presentation.base.viewmodel.MixpanelViewModel

abstract class MixpanelBottomSheetDialogFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val screenName: String,
) :
    BottomSheetDialogFragment() {
    private var _binding: B? = null
    val binding get() = requireNotNull(_binding!!) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }
    abstract val viewModel: MixpanelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(screenName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
