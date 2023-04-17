package org.keepgoeat.presentation.my.withdraw

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWithdrawBinding
import org.keepgoeat.presentation.my.MyViewModel
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showKeyboard
import org.keepgoeat.util.setVisibility

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel: MyViewModel by viewModels()
    lateinit var onGlobalListener: ViewTreeObserver.OnGlobalLayoutListener
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        rootView = binding.root

        initLayout()
        addListeners()
        collectData()
    }

    override fun onStart() {
        super.onStart()
        rootView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
    }

    private fun initLayout() {
        binding.rvWithdraw.apply {
            itemAnimator = null
            adapter = WithdrawReasonAdapter(viewModel::selectReasons)
        }

        initGlobalListener()
    }

    private fun initGlobalListener() {
        onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            viewModel.setKeyboardVisibility(keypadHeight > screenHeight * 0.15)
        }
    }

    private fun addListeners() {
        binding.etOtherReason.setOnFocusChangeListener { _, focused ->
            if (focused) { // '직접 입력' editText가 focus 상태일 때
                requestFocus()
                viewModel.changeCheckboxSelected(true)
            } else {
                clearFocus()
            }
        }
        binding.layoutWithdraw.setOnClickListener { // 외부 영역 클릭 했을 때
            clearFocus()
        }
        binding.btnWithdraw.setOnClickListener {
            if (binding.etOtherReason.text.isNullOrBlank() && viewModel.isOtherReasonSelected.value)
                binding.tvOtherReasonErrorMsg.setVisibility(true)
            else
                WithdrawDialogFragment().show(
                    supportFragmentManager,
                    "withdrawDialog"
                )
        }
        binding.viewWithdrawToolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.isOtherReasonSelected.flowWithLifecycle(lifecycle).onEach { isSelected ->
            if (isSelected) {
                requestFocus()
            } else {
                clearFocus()
            }
        }.launchIn(lifecycleScope)
        viewModel.isValidOtherReason.flowWithLifecycle(lifecycle).onEach { isValid ->
            if (isValid)
                binding.tvOtherReasonErrorMsg.setVisibility(false)
        }.launchIn(lifecycleScope)
        viewModel.isKeyboardVisible.flowWithLifecycle(lifecycle).onEach { isVisible ->
            binding.rvWithdraw.setVisibility(!isVisible)
        }.launchIn(lifecycleScope)
        viewModel.deleteAccountUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> sendDeleteAccountEvent()
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun sendDeleteAccountEvent() {
        val reasons: MutableMap<String, Any> = mutableMapOf()
        viewModel.getWithdrawReasons().map {
            if (it.key == SUBJECTIVE_ISSUE) {
                reasons[it.key] = it.value
            } else {
                reasons[it.key] = getString(it.value as Int)
            }
        }
        viewModel.sendDeleteAccountEvent(reasons)
    }

    private fun clearFocus() {
        binding.etOtherReason.clearFocus()
        showKeyboard(binding.etOtherReason, false)
    }

    private fun requestFocus() {
        binding.etOtherReason.requestFocus()
        showKeyboard(binding.etOtherReason, true)
    }

    override fun onStop() {
        super.onStop()
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
    }

    companion object {
        private const val SUBJECTIVE_ISSUE = "SUBJECTIVE_ISSUE"
    }
}
