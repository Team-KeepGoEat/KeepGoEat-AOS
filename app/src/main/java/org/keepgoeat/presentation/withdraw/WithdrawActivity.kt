package org.keepgoeat.presentation.withdraw

import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWithdrawBinding
import org.keepgoeat.presentation.type.WithdrawCheckType
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showKeyboard

class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel: WithdrawViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.clickType = WithdrawCheckType.DEFAULT
    }

    private fun addListeners() {
        binding.layoutOtherReason.setOnClickListener {
            when (binding.clickType) {
                WithdrawCheckType.CLICKED -> {
                    binding.clickType = WithdrawCheckType.DEFAULT
                    binding.etOtherReason.clearFocus()
                    showKeyboard(binding.etOtherReason, false)
                }
                WithdrawCheckType.DEFAULT -> {
                    binding.clickType = WithdrawCheckType.CLICKED
                    binding.etOtherReason.requestFocus()
                    showKeyboard(binding.etOtherReason, true)
                }
                else -> {}
            }
        }
        binding.etOtherReason.setOnFocusChangeListener { _, focused ->
            if (binding.clickType == WithdrawCheckType.DEFAULT && focused)
                binding.clickType = WithdrawCheckType.CLICKED
        }
        binding.layoutWithdraw.setOnClickListener {
            showKeyboard(binding.etOtherReason, false)
            binding.etOtherReason.clearFocus()
        }
    }
}
