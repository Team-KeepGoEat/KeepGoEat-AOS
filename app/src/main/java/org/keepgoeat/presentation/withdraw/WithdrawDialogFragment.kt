package org.keepgoeat.presentation.withdraw

import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogWithdrawBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class WithdrawDialogFragment :
    BindingDialogFragment<DialogWithdrawBinding>(R.layout.dialog_withdraw) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvWithdraw.setOnClickListener {
            // TODO 탈퇴하기
        }
    }
}
