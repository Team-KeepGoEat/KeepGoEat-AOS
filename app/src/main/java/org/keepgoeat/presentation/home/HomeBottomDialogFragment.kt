package org.keepgoeat.presentation.home

import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomHomeBinding
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

class HomeBottomDialogFragment : BindingBottomSheetDialogFragment<DialogBottomHomeBinding>(R.layout.dialog_bottom_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.layoutHomeBottomMore.setOnClickListener {}
        binding.layoutHomeBottomLess.setOnClickListener {}
    }
}
