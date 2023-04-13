package org.keepgoeat.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogForceUpdateBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class HomeForceUpdateDialogFragment :
    BindingDialogFragment<DialogForceUpdateBinding>(R.layout.dialog_force_update) {
    private var updateVersion: String? = null
    private var currentVersion: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateVersion = arguments?.getString(ARG_UPDATE_VERSION)
        currentVersion = arguments?.getString(ARG_CURRENT_VERSION)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        initLayout()
    }

    private fun initLayout() {
        binding.updateVersion = updateVersion
        binding.currentVersion = currentVersion
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
        const val ARG_CURRENT_VERSION = "currentVersion"
    }
}
