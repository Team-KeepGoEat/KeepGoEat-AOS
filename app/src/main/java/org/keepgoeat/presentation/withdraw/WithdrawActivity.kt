package org.keepgoeat.presentation.withdraw

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    // TODO 키보드 자판에서 키보드 내리는 경우 recyclerView visibility 조정해주기
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
                    clearFocus()
                    binding.clickType = WithdrawCheckType.DEFAULT
                    binding.tvWithdrawEdittextError.visibility = View.GONE
                }
                WithdrawCheckType.DEFAULT -> {
                    requestFocus()
                    binding.clickType = WithdrawCheckType.CLICKED
                }
                else -> {}
            }
        }
        binding.etOtherReason.setOnFocusChangeListener { _, focused ->
            if (focused) { // '직접 입력' editText가 focus 상태일 때
                binding.rvWithdraw.visibility = View.GONE
                if (binding.clickType == WithdrawCheckType.DEFAULT)
                    binding.clickType = WithdrawCheckType.CLICKED
            }
        }
        binding.etOtherReason.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!binding.etOtherReason.text.isNullOrBlank())
                    binding.tvWithdrawEdittextError.visibility = View.GONE
            }
        })
        binding.layoutWithdraw.setOnClickListener { // 외부 영역 클릭 했을 때
            clearFocus()
        }
        binding.btnWithdraw.setOnClickListener {
            if (binding.etOtherReason.text.isNullOrBlank() && binding.clickType == WithdrawCheckType.CLICKED)
                binding.tvWithdrawEdittextError.visibility = View.VISIBLE
            else {
                binding.tvWithdrawEdittextError.visibility = View.GONE
                WithdrawDialogFragment().show(supportFragmentManager, "withDrawDialog")
            }
        }
    }

    private fun clearFocus() {
        binding.etOtherReason.clearFocus()
        binding.rvWithdraw.visibility = View.VISIBLE
        showKeyboard(binding.etOtherReason, false)
    }

    private fun requestFocus() {
        binding.etOtherReason.requestFocus()
        binding.rvWithdraw.visibility = View.GONE
        showKeyboard(binding.etOtherReason, true)
    }
}
