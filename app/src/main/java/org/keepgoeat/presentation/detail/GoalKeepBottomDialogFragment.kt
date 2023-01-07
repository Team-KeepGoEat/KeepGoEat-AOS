package org.keepgoeat.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalKeepBinding
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalKeepBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomGoalKeepBinding>(R.layout.dialog_bottom_goal_keep) {
    private val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.btnKeep.setOnClickListener {
            viewModel.keepGoal()
        }

        binding.tvDelete.setOnClickListener {
            showGoalDeleteDialog()
        }
    }

    private fun showGoalDeleteDialog() {
        GoalDeleteBottomDialogFragment().show(parentFragmentManager, "goalDeleteDialog")
    }
}
