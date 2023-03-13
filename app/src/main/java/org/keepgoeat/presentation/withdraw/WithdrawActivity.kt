package org.keepgoeat.presentation.withdraw

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWithdrawBinding
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.presentation.my.MyViewModel
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.addKeyboardInsetListener
import org.keepgoeat.util.extension.showKeyboard

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        binding.rvWithdraw.apply {
            itemAnimator = null
            adapter = WithdrawReasonAdapter(viewModel::selectReasons)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            binding.layoutWithdraw.addKeyboardInsetListener(viewModel::setKeyboardVisibility)
        }
        binding.layoutOtherReason.isSelected = false
    }

    private fun addListeners() {
        binding.etOtherReason.setOnFocusChangeListener { _, focused ->
            if (focused) // '직접 입력' editText가 focus 상태일 때
                viewModel.changeCheckboxSelected(true)
        }
        binding.layoutWithdraw.setOnClickListener { // 외부 영역 클릭 했을 때
            clearFocus()
        }
        binding.btnWithdraw.setOnClickListener {
            if (!binding.etOtherReason.text.isNullOrBlank() || !viewModel.isOtherReasonSelected.value)
                WithdrawDialogFragment().show(supportFragmentManager, "withDrawDialog")
        }
        binding.viewWithdrawToolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.isOtherReasonSelected.flowWithLifecycle(lifecycle).onEach { isSelected ->
            viewModel.selectReasons(WithdrawReason.REASON5)
            if (isSelected) {
                requestFocus()
            } else {
                clearFocus()
            }
        }.launchIn(lifecycleScope)
    }

    private fun clearFocus() {
        binding.etOtherReason.clearFocus()
        showKeyboard(binding.etOtherReason, false)
    }

    private fun requestFocus() {
        binding.etOtherReason.requestFocus()
        showKeyboard(binding.etOtherReason, true)
    }
}