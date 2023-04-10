package org.keepgoeat.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogForceUpdateBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class HomeForceUpdateDialogFragment :
    BindingDialogFragment<DialogForceUpdateBinding>(R.layout.dialog_force_update) {
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.btnForceUpdate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=" + requireContext().packageName)
            startActivity(intent)
        }
    }
}
