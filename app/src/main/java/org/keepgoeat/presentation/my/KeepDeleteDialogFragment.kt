package org.keepgoeat.presentation.my

import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogKeepDeleteBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class KeepDeleteDialogFragment :
    BindingDialogFragment<DialogKeepDeleteBinding>(R.layout.dialog_keep_delete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.yes.setOnClickListener {
            // TODO 목표 삭제 api 연동
        }
        binding.no.setOnClickListener {
            dismiss()
        }
    }
}
