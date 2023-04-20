package org.keepgoeat.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import org.keepgoeat.BuildConfig
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogForceUpdateBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class HomeForceUpdateDialogFragment :
    BindingDialogFragment<DialogForceUpdateBinding>(R.layout.dialog_force_update) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateVersion = arguments?.getString(ARG_UPDATE_VERSION)
        binding.currentVersion = BuildConfig.VERSION_NAME
        addListeners()
    }

    private fun addListeners() {
        binding.btnForceUpdate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=" + requireContext().packageName)
            startActivity(intent)
        }
    }

    companion object {
        const val ARG_UPDATE_VERSION = "updateVersion"
    }
}
