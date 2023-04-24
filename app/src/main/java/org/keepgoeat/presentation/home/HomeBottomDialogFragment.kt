package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomHomeBinding
import org.keepgoeat.presentation.setting.GoalSettingActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class HomeBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomHomeBinding>(R.layout.dialog_bottom_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(SCREEN_NAME)
    }

    private fun addListeners() {
        binding.layoutHomeBottomMore.setOnClickListener {
            moveToSetting(EatingType.MORE)
            viewModel.sendGoalAddEvent(EatingType.MORE)
        }
        binding.layoutHomeBottomLess.setOnClickListener {
            moveToSetting(EatingType.LESS)
            viewModel.sendGoalAddEvent(EatingType.LESS)
        }
    }

    private fun moveToSetting(eatingType: EatingType) {
        val intent = Intent(requireActivity(), GoalSettingActivity::class.java)
        intent.putExtra(GoalSettingActivity.ARG_EATING_TYPE, eatingType.name)
        startActivity(intent)
        dismiss()
    }

    companion object {
        const val SCREEN_NAME = "main_add"
    }
}
