package org.keepgoeat.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalDeleteBinding
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalDeleteBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomGoalDeleteBinding>(R.layout.dialog_bottom_goal_delete) {
    private val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            viewModel.deleteGoal()
        }
    }
}
