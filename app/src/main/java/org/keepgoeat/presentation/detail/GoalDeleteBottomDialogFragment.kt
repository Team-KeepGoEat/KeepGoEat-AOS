package org.keepgoeat.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalDeleteBinding
import org.keepgoeat.presentation.base.MixpanelBottomSheetDialogFragment

@AndroidEntryPoint
class GoalDeleteBottomDialogFragment :
    MixpanelBottomSheetDialogFragment<DialogBottomGoalDeleteBinding>(
        R.layout.dialog_bottom_goal_delete, SCREEN_NAME
    ) {
    override val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val SCREEN_NAME = "goal delete"
    }
}
