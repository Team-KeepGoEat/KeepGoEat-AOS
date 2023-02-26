package org.keepgoeat.presentation.withdraw

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWithdrawBinding
import org.keepgoeat.domain.model.WithdrawReason
import org.keepgoeat.presentation.type.WithdrawCheckType
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showKeyboard

class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel: WithdrawViewModel by viewModels()
    private var withdrawAdapter = WithdrawReasonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.rvWithdraw.apply {
            itemAnimator = null
            adapter = withdrawAdapter
        }
        withdrawAdapter.submitList(
            listOf(
                WithdrawReason(getString(R.string.withdraw_reason1), WithdrawCheckType.DEFAULT),
                WithdrawReason(getString(R.string.withdraw_reason2), WithdrawCheckType.DEFAULT),
                WithdrawReason(getString(R.string.withdraw_reason3), WithdrawCheckType.DEFAULT),
                WithdrawReason(getString(R.string.withdraw_reason4), WithdrawCheckType.DEFAULT)
            )
        )
        binding.clickType = WithdrawCheckType.DEFAULT
    }

    private fun addListeners() {
        binding.layoutOtherReason.setOnClickListener {
            when (binding.clickType) {
                WithdrawCheckType.CLICKED -> {
                    binding.rvWithdraw.visibility = View.VISIBLE
                    binding.clickType = WithdrawCheckType.DEFAULT
                    binding.etOtherReason.clearFocus()
                    showKeyboard(binding.etOtherReason, false)
                }
                WithdrawCheckType.DEFAULT -> {
                    binding.rvWithdraw.visibility = View.GONE
                    binding.clickType = WithdrawCheckType.CLICKED
                    binding.etOtherReason.requestFocus()
                    showKeyboard(binding.etOtherReason, true)
                }
                else -> {}
            }
        }
        binding.etOtherReason.setOnFocusChangeListener { _, focused ->
            if (focused) { // 직접 입력 editText가 focus 상태일 때
                binding.rvWithdraw.visibility = View.GONE
                if (binding.clickType == WithdrawCheckType.DEFAULT)
                    binding.clickType = WithdrawCheckType.CLICKED
            }

        }
        binding.layoutWithdraw.setOnClickListener { // 외부 영역 클릭 했을 때
            showKeyboard(binding.etOtherReason, false)
            binding.etOtherReason.clearFocus()
            binding.rvWithdraw.visibility = View.VISIBLE
        }
        binding.btnWithdraw.setOnClickListener {
            // TODO
        }
    }
}
