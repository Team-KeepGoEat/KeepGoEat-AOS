package org.keepgoeat.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import org.keepgoeat.BuildConfig
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogForceUpdateBinding
import org.keepgoeat.presentation.base.screen.BindingDialogFragment

class HomeForceUpdateDialogFragment :
    BindingDialogFragment<DialogForceUpdateBinding>(R.layout.dialog_force_update) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updateVersion = arguments?.getString(ARG_UPDATE_VERSION)
        binding.currentVersion = BuildConfig.VERSION_NAME

        addListeners()
    }

    private fun addListeners() {
        binding.btnForceUpdate.setOnClickListener {
            moveToPlayStore()
        }
    }

    private fun moveToPlayStore() {
        Intent(Intent.ACTION_VIEW).apply {
            data =
                Uri.parse(getString(R.string.play_store_detail_url) + requireContext().packageName)
        }.also {
            startActivity(it)
        }
    }

    companion object {
        const val ARG_UPDATE_VERSION = "updateVersion"
    }
}
